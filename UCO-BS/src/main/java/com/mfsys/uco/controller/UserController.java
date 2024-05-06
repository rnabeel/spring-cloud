package com.mfsys.uco.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.mfsys.uco.UCOURI;
import com.mfsys.uco.dto.*;
import com.mfsys.uco.dto.webclientdto.AccountDetail;
import com.mfsys.uco.model.CustomerProfile;
import com.mfsys.uco.model.UcoAccount;
import com.mfsys.uco.repository.UCOAccountRepository;
import com.mfsys.uco.service.CustomerProfileService;
import com.mfsys.uco.service.TransactionPinService;
import com.mfsys.uco.service.TransactionService;
import com.mfsys.uco.service.UcoAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final TransactionPinService transactionPinService;
    private final TransactionService transactionService;
    private final UcoAccountService ucoAccountService;
    private final CustomerProfileService customerProfileService;

    @PostMapping(UCOURI.ONBOARD_CUSTOMER)
    public ResponseEntity<HttpStatus>  customerOnBoarding(@RequestBody SignupStep3RequestModel signupStep3RequestModel) {
        ucoAccountService.onBoardCustomer(signupStep3RequestModel);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(UCOURI.VIEW_BALANCE)
    public ViewBalanceResponseModel viewBalance(@RequestBody ViewBalanceRequestModel viewBalanceRequestModel) {
        ViewBalanceResponseModel viewBalanceResponseModel = new ViewBalanceResponseModel();
        viewBalanceResponseModel.setMbmBkmsbalance(ucoAccountService.fetchAccountBalance(viewBalanceRequestModel.getPorOrgacode(),viewBalanceRequestModel.getMbmBkmsNumber()));
        return viewBalanceResponseModel;
    }

    @GetMapping(UCOURI.FETCH_LOGIIN_DATA)
    public CustomerProfile fetchlogindata(
            @RequestParam String porOrgacode,
            @RequestParam String email) {
        return customerProfileService.fetchCustcodeBasedOnEmail(porOrgacode,email);
    }

    @GetMapping(UCOURI.FETCH_DEPOSITACCOUNTS)
    public List<AccountDetail> getDepositAccounts(
            @RequestParam String porOrgacode,
            @RequestParam String cmpCustcode,
            @RequestParam String pctCstycode) {
        return List.of(ucoAccountService.fetchdepositAccountFromCiihive(porOrgacode,cmpCustcode));
    }

    @GetMapping(UCOURI.FETCH_ACCOUNT_STATEMENT)
    public List<DepositAccountTransaction> getAccountStatement(
            @RequestParam String porOrgacode,
            @RequestParam String mbmBkmsnumber,
            @RequestParam String sgtGntrvaluedatefrom,
            @RequestParam String sgtGntrvaluedateto
    ) {

        List<DepositAccountTransaction> transactions = new ArrayList<>();
        DepositAccountTransaction transaction = new DepositAccountTransaction();
        transaction.setTranID("12345");
        transaction.setSgtGntrCreatedAt("2024-03-17");
        transaction.setSgtGntrNarration("Sample Transaction");
        transaction.setSgtGntrvaluedate("2024-03-17");
        transaction.setDeposit("100.00");
        transaction.setWithdrawal("0.00");
        transaction.setStatus("approved");
        transaction.setSgtGntramt("1000.00");
        transactions.add(transaction);
        return transactions;
    }

    @GetMapping(UCOURI.FETCH_ACCOUNT_INQUIRY)
    public AccountInquiryResponse getAccountInquiry(
            @RequestParam String acntTypeCode,
            @RequestParam String acntTypeValue,
            @RequestParam String porOrgacode,
            @RequestParam String channelCode) {
        return  ucoAccountService.fetchAccountTitile(porOrgacode,acntTypeCode,acntTypeValue);
    }

    // mine

//    @PostMapping(UCOURI.SUBMIT_TRANSACTION)
//    public TransactionResponseModel submitTransaction(@RequestBody TransactionRequestModel transactionRequestModel) {
//        String mockTranID = "TRAN1234567890";
//        return new TransactionResponseModel(mockTranID);
//    }

    @PostMapping(UCOURI.GENERATE_TRANSACTIONS_REPORT)
    public String generateReport(@RequestBody TransactionHistoryRequest request) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Account Transaction History Report"));
            document.add(new Paragraph("Organization Code: " + request.getPorOrgacode()));
            document.add(new Paragraph("Account Number: " + request.getMbmBkmsnumber()));
            document.add(new Paragraph("From: " + request.getSgtGntrvaluedatefrom() + " To: " + request.getSgtGntrvaluedateto()));
            document.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            return "Error generating report";
        }
    }
    @PostMapping(UCOURI.CREATE_TRAN_PIN)
    public ResponseEntity<HttpStatus> createTransactionPin(@RequestBody CreateTransactionPinRequest request) {
        try {
            transactionPinService.createTransactionPin(request);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
             return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(UCOURI.VERIFY_TRAN_PIN)
    public ResponseEntity<?> verifyPin(@RequestBody VerifyPinRequest request) {
        try {
            boolean isVerified = transactionPinService.verifyOTPAndSavePin(request);
            if (isVerified) {
                return ResponseEntity.ok("OTP PIN verified and tran pin changed successfully.");
            } else {
                return ResponseEntity.badRequest().body("PIN verification failed.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during PIN verification: " + e.getMessage());
        }
    }

    @PutMapping(UCOURI.CHANGE_TRAN_PIN)
    public ResponseEntity<String> updateTransactionPin(@RequestBody ChangeTransactionPinRequest request) {
        try {
            transactionPinService.updateTransactionPin(request);
            return ResponseEntity.ok("OTP sent");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred.");
        }
    }

    @GetMapping(UCOURI.FETCH_EXCHANGE_RATE)
    public Object fetchExchangeRate(
            @RequestParam String porOrgacode) {
        return ucoAccountService.fetchExchangeRate(porOrgacode);
    }
}
