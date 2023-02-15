package com.synergy.transaction.controller;

import com.synergy.transaction.config.CloudFolder;
import com.synergy.transaction.dto.PostBookingDto;
import com.synergy.transaction.dto.UploadProofOfPayment;
import com.synergy.transaction.repository.BookingRepository;
import com.synergy.transaction.repository.ProfileRepository;
import com.synergy.transaction.repository.TransactionRepository;
import com.synergy.transaction.service.impl.TransactionServiceImpl;
import com.synergy.transaction.util.Response;
import com.synergy.transaction.util.UploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/seeker/transactions")
@CrossOrigin("*")
public class TransactionControllerSeeker {
    private final static Logger logger = LoggerFactory.getLogger(TransactionControllerSeeker.class);

    @Autowired
    UploadFile uploadFile;

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private Response res;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/list/{profileId}")
    public ResponseEntity<Map<String, Object>> getTransactionListByIdSeeker(
            @PathVariable Long profileId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        try {
            Pageable pagination = PageRequest.of(page, size);
            Page<Map<String, Object>> seekerTransactions = transactionServiceImpl.getSeekerTransactions(profileId, pagination);

            return res.resSuccess(seekerTransactions, "success", 200);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return res.internalServerError(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{transactionId}")
    public ResponseEntity<Map<String, Object>> cancelTransactionBySeeker(@PathVariable("transactionId") Long transactionId) {
        try {
            boolean deleteTransaction = transactionServiceImpl.cancelTransaction(transactionId);
            if (!deleteTransaction) {
                return res.clientError("transaction doesn't exist");
            }
            return res.resSuccess(1, "success", 200);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return res.internalServerError(e.getMessage());
        }
    }

    @PostMapping(value = "/user/{profileId}/price/{priceId}")
    public ResponseEntity<Map<String, Object>> createBookingRoom(
            @PathVariable("profileId") Long profileId,
            @PathVariable("priceId") Long priceId,
            @ModelAttribute @Valid PostBookingDto postBookingDto
    ) {
        try {
            return transactionServiceImpl.bookKost(profileId, priceId, postBookingDto);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return res.internalServerError(e.getMessage());
        }
    }

    @PutMapping(value = "/transaction/{transactionId}")
    public ResponseEntity<Map<String, Object>> uploadTransactionSpoofImage(
            @PathVariable("transactionId") Long transactionId,
            @ModelAttribute @Valid UploadProofOfPayment uploadProofOfPayment
    ) {
        try {
            return transactionServiceImpl.uploadProofOfPayment(transactionId, uploadProofOfPayment);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return res.internalServerError(e.getMessage());
        }
    }

    @GetMapping("/download/{transactionId}")
    public ResponseEntity<byte[]> downloadTransactionProofOfPayment(@PathVariable("transactionId") Long transactionId) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "Kosanku_Invoice.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            byte[] pdfBytes = transactionServiceImpl.downloadInvoice(transactionId);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
