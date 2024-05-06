package com.mfsys.uco.service;

import com.mfsys.comm.util.FunctionReturnDetail;
import com.mfsys.uco.UCOURI;
import com.mfsys.uco.dto.*;
import com.mfsys.uco.dto.Transaction.TransactionOtpRequestModel;
import com.mfsys.uco.exception.SameCrDrAccountExistsException;
import com.mfsys.uco.model.CustomerProfile;
import com.mfsys.uco.model.CustomerProfileId;
import com.mfsys.uco.model.TransactionTrail;
import com.mfsys.uco.repository.CustomerProfileRepository;
import com.mfsys.uco.repository.TransactionTrailRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@Data
@RequiredArgsConstructor
public class TransactionService {

    private final CustomerProfileRepository customerProfileRepository;
    private final NotificationService notificationService;
    private final TransactionPinService transactionPinService;
    private final TransactionTrailRepository transactionTrailRepository;
    private final WebClientDepositService webClientDepositService;

    public TransactionPinResponseModel sendOtpAndValidateTranPin(TransactionOtpRequestModel transactionOtpRequestModel){
       CustomerProfile customerProfile =  verifyOldPinAndGetCmpProfile(transactionOtpRequestModel.getPorOrgacode(),
               transactionOtpRequestModel.getTransPincode(),transactionOtpRequestModel.getCmpCustcode());
      return TransactionPinResponseModel.builder().notificationId(transactionPinService.sendOtp(customerProfile, transactionOtpRequestModel.getChannelCode(),
              transactionOtpRequestModel.getPinType(), "Transaction Verification OTP", transactionOtpRequestModel.isOtpRequired())).build();
    }

    public Map<String,Object> cashInTransaction(CashInTransactionRequest transactionRequest) {
        validation(transactionRequest);
       TransactionTrail transactionTrail = TransactionTrail.builder()
                .porOrgacode(transactionRequest.getPorOrgacode())
                .drMbmBkmsnumber(transactionRequest.getDrMbmBkmsnumber())
                .crMbmBkmsnumber(transactionRequest.getCrMbmBkmsnumber())
                .dmpProdcode(transactionRequest.getDmpProdCode())
                .drmbmBkmstitle(transactionRequest.getDrMbmBkmstitle())
                .drpcrCurrdesc(transactionRequest.getCrPcrCurrcode())
                .cmpCustcode(transactionRequest.getCmpCustcode())
                .drPcrCurrcode(transactionRequest.getDrPcrCurrcode())
                .crMbmBkmstitle(transactionRequest.getCrMbmBkmstitle())
                .crPcrCurrdesc(transactionRequest.getCrPcrCurrdesc())
                .crPcrCurrcode(transactionRequest.getCrPcrCurrcode())
                .sgtSentGntrnumber(null)
                .drSgtGntrdate(LocalDate.now())
                .sgtGntramt(BigDecimal.valueOf(transactionRequest.getSgtGntramtfc()))
                .batAcnttranSend(false)
                .batAcnttranReceived(false)
                .sgtReceiveGntrnumber(null)
                .build();

        CoreCashInTransaction coreCashInTransaction = CoreCashInTransaction.builder()
                .drMbmBkmsnumber(transactionRequest.getDrMbmBkmsnumber())
                .drPcrCurrcode("123")
                .sgtGntramtfc(transactionRequest.getSgtGntramtfc())
                .otdTrancomment(transactionRequest.getOtdTrancomment())
                .porOrgacode(transactionRequest.getPorOrgacode())
                .build();
        Map<String,Object> response = (Map<String,Object>)webClientDepositService.postTransaction(coreCashInTransaction, UCOURI.BANKING_CASH_IN,transactionRequest.getPorOrgacode());
            Map<String,Object> transactionId = (Map<String,Object>) response.get("FuncReturnDetail");
            transactionTrail.setSgtSentGntrnumber(extractTranNumber(List.of(transactionId.get("arguments"))));
            transactionTrail.setBatAcnttranSend(true);
            transactionTrailRepository.save(transactionTrail);
            return response;
    }

    private void validation(CashInTransactionRequest transactionRequest) {
        if(transactionRequest.getCrMbmBkmsnumber().equals(transactionRequest.getDrMbmBkmsnumber())){
            throw new SameCrDrAccountExistsException();
        }
        notificationService.verifyOtpViaOtpId(transactionRequest.getNotificationId(),transactionRequest.getPinType(),transactionRequest.getObpPincode());
    }

    public List<TransactionTrail> fetchPendingCrTransactions(String porOrgacode, String mbmBkmsnumber) {
        Optional<List<TransactionTrail>> optionalTransactions = transactionTrailRepository.findByPorOrgacodeAndCrMbmBkmsnumberAndBatAcnttranReceivedFalse(porOrgacode, mbmBkmsnumber);
        return optionalTransactions.orElseGet(Collections::emptyList);
    }

    public List<TransactionTrail> fetchDepositAccountStatement(String porOrgacode, String mbmBkmsnumber) {
        Optional<List<TransactionTrail>> optionalTransactions = transactionTrailRepository.fetchDepositAccountStatement( mbmBkmsnumber);
        return optionalTransactions.orElseGet(Collections::emptyList);
    }

    public Map<String,Object> cashOutTransaction(CashOutTransactionRequest cashOutTransactionRequest) {
        verifyOldPinAndGetCmpProfile(cashOutTransactionRequest.getPorOrgacode(),
                cashOutTransactionRequest.getCmpTranpin(),cashOutTransactionRequest.getCmpCustcode());
        Map<String,Object> response = new HashMap<>();
        Optional<TransactionTrail> transactionTrail = transactionTrailRepository.findById(Math.toIntExact(cashOutTransactionRequest.getId()));
        if(transactionTrail.isPresent()) {
            CoreCashOutTransaction cashOutTransaction = CoreCashOutTransaction.builder()
                    .crPcrCurrcode("123")
                    .crMbmBkmsnumber(transactionTrail.get().getCrMbmBkmsnumber())
                    .porOrgacode(cashOutTransactionRequest.getPorOrgacode())
                    .otdTrancomment(cashOutTransactionRequest.getId() + "_Received")
                    .sgtGntramtfc(transactionTrail.get().getSgtGntramt())
                    .build();

             response = (Map<String,Object>) webClientDepositService.postTransaction(cashOutTransaction, UCOURI.BANKING_CASH_OUT, transactionTrail.get().getPorOrgacode());
                Map<String,Object> transactionId = (Map<String,Object>) response.get("FuncReturnDetail");
                transactionTrail.get().setSgtReceiveGntrnumber(extractTranNumber(List.of(transactionId.get("arguments"))));
                transactionTrail.get().setBatAcnttranReceived(true);
                transactionTrailRepository.save(transactionTrail.get());
        }
        return response;
    }

    private CustomerProfile verifyOldPinAndGetCmpProfile(String porOrgacode, String transPincode,String cmpCustcode) {
       CustomerProfile customerProfile =  transactionPinService.fetchCustomer(porOrgacode,
               cmpCustcode);
        transactionPinService.validateOldPin(customerProfile,transPincode);
        return customerProfile;
    }

    private CustomerProfile fetchCustomerBasedOnCmpCustcode(String porOrgacode, String cmpCustcode) {
        return customerProfileRepository.findById(new CustomerProfileId(porOrgacode, cmpCustcode))
                .orElseThrow(() -> new IllegalArgumentException("Customer profile not found for ID: " + cmpCustcode));

    }

    private String extractTranNumber( List<Object> args ){
        if (args != null && args.size() > 0) {
            return String.valueOf(args.get(0)).replace("[", "").replace("]", "");
        }
        throw new RuntimeException("may day");
    }
}
