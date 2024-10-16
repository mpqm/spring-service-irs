package com.example.api.domain.total_process.controller;

import com.example.api.domain.resume.model.response.ResumeReadAllRes;
import com.example.api.domain.total_process.model.request.TotalProcessCreateReq;
import com.example.api.domain.total_process.model.response.TotalProcessCreateRes;
import com.example.api.domain.total_process.model.response.TotalProcessReadAllRes;
import com.example.api.domain.total_process.service.TotalProcessService;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponse;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.api.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/total-process")
public class TotalProcessController {

    private final TotalProcessService totalProcessService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<TotalProcessCreateRes>> create(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody TotalProcessCreateReq dto) throws BaseException {
        TotalProcessCreateRes response = totalProcessService.create(dto, customUserDetails);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.TOTAL_PROCESS_CREATE_SUCCESS, response));
    }

    // (채용담당자) 공고에 지원한 지원자 결과 내역 페이징 처리
    @GetMapping("/recruiter/read-all")
    public ResponseEntity<BaseResponse<ResumeReadAllRes>> readAllRecruiter(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Long announcementIdx, Integer page, Integer size) throws BaseException {

        Page<TotalProcessReadAllRes> response = totalProcessService.readAll(
                customUserDetails, announcementIdx, page, size
        );
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.TOTAL_PROCESS_READ_SUCCESS, response));
    }
}