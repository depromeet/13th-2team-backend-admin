package com.depromeet.franchise.controller;


import com.depromeet.common.response.ApiCommonResponse;
import com.depromeet.common.response.ApiMessageResponse;
import com.depromeet.franchise.dto.request.CreateFranchiseRequest;
import com.depromeet.franchise.dto.request.ModifyFranchiseImageRequest;
import com.depromeet.franchise.dto.response.FranchiseResponse;
import com.depromeet.franchise.service.FranchiseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/franchise")
public class FranchiseController {

    private final FranchiseService franchiseService;

    @Operation(summary = "프랜차이즈 생성", description = "프랜차이즈 생성 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 프랜차이즈가 생성되었습니다."),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 프랜차이즈입니다.")
    })
    @PostMapping
    public ResponseEntity<ApiMessageResponse> franchiseSave(@RequestBody final CreateFranchiseRequest request) {
        franchiseService.saveFranchise(request);
        return ResponseEntity.ok()
                .body(ApiMessageResponse.of(HttpStatus.OK, "프랜차이즈 저장 성공"));
    }

    @Operation(summary = "프랜차이즈 이미지 변경", description = "프랜차이즈 이미지 수정 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 프랜차이즈가 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "존재 하지 않는 프랜차이즈입니다."),
            @ApiResponse(responseCode = "409", description = "같은 이미지 입니다.")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ApiMessageResponse> franchiseModifyImage(@PathVariable(name = "id") final Long id,
                                                                        @RequestBody final ModifyFranchiseImageRequest request){
        franchiseService.modifyFranchiseImage(id, request);
        return ResponseEntity.ok()
                .body(ApiMessageResponse.of(HttpStatus.OK, "프랜차이즈 수정 성공"));
    }

    @Operation(summary = "프랜차이즈 전체 목록 조회", description = "프랜차이즈 전체 목록 조회 API입니다.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "성공적으로 프랜차이즈 목록을 조회했습니다."))
    @GetMapping
    public ResponseEntity<ApiCommonResponse<List<FranchiseResponse>>> franchises(){
        final var franchises = franchiseService.findAllFranchise();
        return ResponseEntity.ok()
                .body(ApiCommonResponse.of(HttpStatus.OK, "프랜차이즈 전체 목록 조회 성공", franchises));
    }
}
