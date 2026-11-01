# SPRING-SERVICE-IRS 백엔드 API 명세서

# 🙂 Auth

- **`[POST]` 채용담당자 회원 가입**
    
    
    | 요구사항 ID | **RECRUITER_001** |
    | --- | --- |
    | Description | 채용담당자는 회원 정보를 입력해 회원가입 진행 |
    | URL | /api/auth/signup |
    | Auth Required | No |
    | 참고 | [https://www.jobkorea.co.kr/Join/GI_Regist](https://www.jobkorea.co.kr/Join/GI_Regist) |
    
    | **`body params (form-data)`** | **`요구 사항 상세`** |
    | --- | --- |
    | dto: {
       "role": "ROLE_RECRUITER",
       "email": "recruiter@recruiter.com",
       "password": "1234",
       "name": "채용담당자1",
       "phone_number": "010-1234-1234"
    }
    file: {
       image: 채용담당자 프로필 이미지
    } | **[채용담당자 회원 가입]**
    1. 기업 인증이 선행, 클라이언트 단에서 기업인증이 되지않으면 회원가입 버튼을 출력하지 않는다.
    2. 회원가입시 입력한 Email로 인증 코드가 발급되기에 인증 가능한 Email이여야 하고, 이메일 중복은 불가능
    3. 채용담당자로 회원가입하는 경우 해당 이메일을 다른 역할(지원자, 면접관)의 회원가입으로 사용할 수 없음
    4. 비밀번호는 대문자 및 특수문자를 1개 이상 포함해야하며 길이는 최소 8자 최대 15자까지 가능
    5. 회원 가입시 이름은 최대 10자이며, 영어/한글/숫자만 가능
    6. 비활성화된 회원이 다시 가입한 경우 enabled(0) inactive(1) → enabled(0) inactive(0) 초기화 후 이메일인증
    
    **[기업 인증]**
    1. 사업자등록번호(필수): 숫자로 이루어진 10자리 값만 가능 (기호 제거)
    2. 대표자성명(필수): 외국인 사업자의 경우에는 영문명 입력
    3. 개업일자(필수): 사업자등록증에 표기된 개업연월일 날짜, YYYYMMDD 포맷의 날짜로 입력(기호 제거)
    4. 폼에서 `[POST]` 기업인증을 호출, json 양식에 맞게 매핑 필요
    
    **[파일 업로드]**
    1. 프로필 이미지 값이 없으면 예외를 발생시키므로 이미지 업로드 서비스에서 이를 처리
    
    **[이메일 인증]**
    1. 홈페이지에서 가입버튼을 누르면 이메일 인증 링크를 전송
    2. 이메일 인증 링크를 클릭시 클라이언트 로그인 페이지로 리다이렉션
    
    **~~[필수동의 항목 및 개인정보 수집 및 이용 동의]**
    1. 클라이언트에서 처리
    2. 필수 약관이나 전체 동의 안하면 가입 불가~~ |
    - **✅ Response 200**
        
        ```json
            ```json
            {
                "code": "1000",
                "isSuccess": true,
                "message": "회원가입에 성공했습니다. 이메일 인증 후 로그인 해주세요",
                "result": {
        						"idx": 1,
        						"email": "recruiter@recruiter.com",
        						"inactive": 0,
        						"email_auth": 0,
        						"role": "ROLE_RECRUITER"
                }
            }
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
            {
                "code": "1001",
                "isSuccess": false,
                "message": "회원가입에 실패했습니다.",
                "result": {
        	         {error.details} || null
                }
            }
        	```
        ```
        
- **`[POST]` 지원자 회원 가입**
    
    
    | **요구사항 ID** | **SEEKER_001** |
    | --- | --- |
    | Description | 지원자는 회원 정보를 입력해 회원가입 진행 |
    | URL | /api/auth/signup |
    | Auth Required | No |
    | 참고 | [https://www.jobkorea.co.kr/Join/GI_Regist](https://www.jobkorea.co.kr/Join/M_Regist?OEM_No=1&Org_OEM_No=1) |
    
    | **`body params (form-data)`** | **`요구 사항 상세`** |
    | --- | --- |
    | dto: {
       "role": "ROLE_SEEKER",
       "email": "[seeker@seeker.com](mailto:seeker2@seeker.com)",
       "password": "1234",
       "name": "seeker",
       "nickname":"seeker",
       "gender": false,
       "birth": "20241212",
       "address": "서울시 동작구 보라매로",
       "socialType": "normal",
       "phone_number": "010-1234-1234"
    }
    file: {
       image: 지원자 프로필 이미지
    } | **[지원자 회원 가입]**
    1. 회원가입시 입력한 Email로 인증 코드가 발급되기에 인증 가능한 Email이여야 하고, 이메일 중복은 불가능
    2. 지원자로 회원가입하는 경우 해당 이메일을 다른 역할(채용담당자, 면접관)의 회원가입으로 사용할 수 없음
    3. 비밀번호는 대문자 및 특수문자를 1개 이상 포함해야하며 길이는 최소 8자 최대 15자까지 가능
    4. 회원 가입시 이름은 최대 10자이며, 영어/한글/숫자만 가능
    5. 비활성화된 회원이 다시 가입한 경우 enabled(0) inactive(1) → enabled(0) inactive(0) 초기화 후 이메일인증
    
    **[파일 업로드]**
    1. 프로필 이미지 값이 없으면 예외를 발생시키므로 이미지 업로드 서비스에서 이를 처리
    
    **[이메일 인증]**
    1. 홈페이지에서 가입버튼을 누르면 이메일 인증 링크를 전송
    2. 이메일 인증 링크를 클릭시 클라이언트 로그인 페이지로 리다이렉션
    
    **~~[필수동의 항목 및 개인정보 수집 및 이용 동의]**
    1. 클라이언트에서 처리
    2. 필수 약관이나 전체 동의 안하면 가입 불가~~ |
    - **✅ Response 200**
        
        ```json
            ```json
            {
                "code": "200",
                "isSuccess": true,
                "message": "회원가입에 성공했습니다. 이메일 인증 후 로그인 해주세요",
                "result": {
        						"idx": 1,
        						"email": "seeker@seeker.com",
        						"inactive": 0,
        						"email_auth": 0,
        						"role": "ROLE_SEEKER"
                }
            }
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
          ```json
            {
                "code": "300",
                "isSuccess": false,
                "message": "회원가입에 실패했습니다.",
                "result": {
        	         {error.details} || null
                }
            }
        	```
        ```
        
- **`[GET]` 이메일 인증**
    
    
    | **요구사항 ID** | **RECRUITER_002** |
    | --- | --- |
    | Description | 채용담당자는 인증 메일을 통해 이메일 인증을 시도한다. |
    | URL | /api/auth/email-verify?email={email}&role={role}&uuid={uuid} |
    | Auth Required | No |
    
    | **`request params`** | **`요구 사항 상세`** |
    | --- | --- |
    | email, role, uuid | **[이메일 인증을 통한 본인 인증]**
    1. 회원가입시: enabled(0) inactive(0)
    2. 이메일인증완료: enabled(1) inactive(0)
    3. 받아온 파라미터 uuid와 email을 이용해 emailVerify 테이블을 조회
    4. 일치하는 데이터행 찾아서 비교
    5. 일치하는 값을 찾으면 메인페이지로 리다이렉션 |
    - **✅ Response 200**
        
        ```json
            ```json
            {
                "code": "200",
                "isSuccess": true,
                "message": "이메일 검증을 완료했습니다.",
                "result": {
                }
            }
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
          ```json
            {
                "code": "300",
                "isSuccess": false,
                "message": "이메일 검증을 실패했습니다.",
                "result": {
        	         {error.details} || null
                }
            }
        	```
        ```
        
- **`[POST]` 기업 인증**
    
    
    | **요구사항 ID** | **RECRUITER_003** |
    | --- | --- |
    | Description | 채용담당자는 국세청_사업자등록정보 진위확인 서비스 (외부 API) 를 이용해 기업 인증을 수행한다. |
    | URL | /api/auth/company-verify
    **[OpenFeign]** http://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=[서비스키] |
    | Auth Required | wVeyLzuXvFUfS2bvKRPz4w50zD0iAJkZLqqLJ5ZWTFrl69dM+Vk09O2nQkRdTzYDLwezVTf12yqFqYFlQHu5TQ== |
    | 참고 | [https://imspear.tistory.com/114](https://imspear.tistory.com/114)
    [https://www.data.go.kr/data/15081808/openapi.do#/사업자등록정보 진위확인 API/validate](https://www.data.go.kr/data/15081808/openapi.do#/%EC%82%AC%EC%97%85%EC%9E%90%EB%93%B1%EB%A1%9D%EC%A0%95%EB%B3%B4%20%EC%A7%84%EC%9C%84%ED%99%95%EC%9D%B8%20API/validate)  |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | **[Json]**
    {
       "crn": "0000000000",
       "opened_at": "20000101",
       "ceo_name": "홍길동",
       "recruiter_email": "[recruiter@recruiter.com](mailto:okqkrwhdtjd@gmail.com)",
       "company_secret_code":    "TESTCOMPANYSECRETCODE"
    }
    
    **[OpenFeign]**
    {
       "businesses": [
          {
             "b_no": "0000000000",
             "start_dt": "20241212",
             "p_nm": “IRS"
          }
       ]
    } | **[회원가입 전 채용담당자 기업인증]**
    1. 사업자등록정보 진위확인 AP 를 OpenFeign으로 서비스 로직에서 실행하고 인증 값을 반환
    2. 사업자등록정보 진위확인 API에서 참을 반환하면 채용담당자의 이메일과 함께 companyVerify 테이블에 저장
    ~~3. 시스템 관리자와 기업간의 인증코드를 서로 공유해 SecretCode로 저장~~
    
    **[사업자등록정보 진위확인 API]**
    1. 사업자등록번호(필수): 숫자로 이루어진 10자리 값만 가능 ('-' 등의 기호 반드시 제거 후 호출)하다.
    2. 대표자성명(필수): 외국인 사업자의 경우에는 영문명 입력한다.
    3. 개업일자(필수): 사업자등록증에 표기된 개업연월일 날짜, YYYYMMDD 포맷의 날짜로 입력(기호 제거)한다.
    4. 사업자 등록번호 인증에 성공하면 companyVerify table의 company_auth를 1로 설정한다. |
    - **✅ Response 200**
        
        ```json
            ```json
            {
                "code": "200",
                "isSuccess": true,
                "message": "기업 인증을 완료했습니다.",
                "result": {
        		        "recruiter_email" : "recruiter@recruiter.com",
        		        "auth_success": true
                }
            }
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
          ```json
            {
                ****"code": "300",
        ****        "isSuccess": false,
                "message": "기업 인증에 실패했습니다.",
                "result": {
        	         {error.details} || null
                }
            }
        	```
        ```
        
- **`[POST]` 로그인**
    
    
    | **요구사항 ID** | **RECRUITER_004, SEEKER_003** |
    | --- | --- |
    | Description | 채용담당자는 이메일과 비밀번호를 입력하여 로그인을 진행한다. |
    | URL | /api/auth/login |
    | Auth Required | No |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | {
       “email": "member@member.com",
       "password": “qwer1234”,
    } | **[사용자가 로그인 정보를 입력하여 로그인]**
    1. 이메일은 지원자, 채용담당자, 면접관 중 하나의 역할만 사용 중복 안함
    2. 비밀번호: 영어 대, 소문자, 특수문자 포함 8자 이상, 암호화해 저장
    3. 이메일 또는 비밀번호 입력하지 않을 시 경고 문구 생성 생성
    4. 하나의 API에 지원자, 채용담당자, 면접관을 구분하는 서비스 로직 필요
    5. 로그인시 RefreshToken, AccessToken 발급 |
    - **✅ Response 200**
        
        ```json
            ```json
            {
                "code": "200",
                "isSuccess": true,
                "message": "로그인 성공",
                "result": {
                }
            }
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
            {
                "code": "300",
                "isSuccess": false,
                "message": "로그인 실패",
                "result": {
        	         {error.details} || null
                }
            }
            ```
        ```
        
- **`[POST]` 로그아웃**
    
    
    | **요구사항 ID** | **COMMON_AUTH_001** |
    | --- | --- |
    | Description | 지원자, 채용담당자 로그아웃 |
    | URL | /api/auth/logout |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | x
     | **[로그아웃 버튼을 눌러 로그아웃]**
    1. 클라이언트: 세션 스토리지 삭제
    2. 쿠키: AccessToken, RefreshToken삭제 |
    - **✅ Response 200**
        
        ```json
            ```json
            {
                "code": "200",
                "isSuccess": true,
                "message": "로그아웃 성공",
                "result": {
                }
            }
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
            {
                "code": "300",
                "isSuccess": false,
                "message": "로그아웃 실패",
                "result": {
        	         {error.details} || null
                }
            }
            ```
        ```
        
- **`[GET]` 지원자 정보 상세 조회**
    
    
    | **요구사항 ID** | **MEMBER_PAGE_002** |
    | --- | --- |
    | Description | 지원자의 정보를 조회 한다. |
    | URL | /api/auth/seeker/read |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | **X** | **[지원자의 정보를 조회]**
    1. seeker 테이블에서 로그인된 회원의 idx와 일치하는 정보를 조회
    2. 조회 정보
    - 이메일
    - 이름
    - 닉네임
    - 성별
    - 생년월일
    - 핸드폰
    - 주소
    - 소셜 로그인 타입
    - 프로필 사진
    - 생성 날짜 / 수정 날짜 |
    - **✅ Response 200**
        
        ```json
            ```json
            {
                "code": "200",
                "isSuccess": true,
                "message": "회원 정보 조회에 성공했습니다.",
                "result": {
        		        "email": "seeker@seeker.com",
        		        "name": "지원자",
        		        "nickname": "지원자",
        		        "gender": "여",
        		        "birth": "2020-12-12",
        		        "phone": "01012345678",
        		        "address": "울산광역시 중구 성안동",
        		        "createdAt" : "2024-09-03 15:30:45",
        		        "updatedAt" : "2024-09-03 15:30:50"
                }
            }
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
            {
                "code": "300",
                "isSuccess": false,
                "message": "회원 정보 조회에 실패했습니다.",
                "result": {
        						{error.details} || null
                }
            }
            ```
        ```
        
- **`[GET]` 채용담당자 정보 상세 조회**
    
    
    | **요구사항 ID** | **RECRUITER_PAGE_002** |
    | --- | --- |
    | Description | 채용담당자의 정보를 조회한다. |
    | URL | /api/auth/read-recruiter-info |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | **X** | **[채용 담당자의 정보를 조회]**
    1. recruiter 테이블에서 로그인된 회원의 idx와 일치하는 정보를 조회
    2. 조회 정보
    - 채용담당자 이름
    - 채용담당자 연락처
    - 채용담당자 이메일 |
    - **✅ Response 200**
        
        ```json
            ```json
            {
                "code": "200",
                "isSuccess": true,
                "message": "회원 정보 조회에 성공했습니다.",
                "result": {
        		        "managerEmail": "recruiter@recruiter.com",
        		        "managerName": "채용담당자",
        		        "managerContact": "01012341234",
        		        "phone1": 010,
        		        "phone2": 1234,
        		        "phone3": 1234
                }
            }
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
            {
                "code": "300",
                "isSuccess": false,
                "message": "회원 정보 조회에 실패했습니다.",
                "result": {
        						{error.details} || null
                }
            }
            ```
        ```
        

---

# 📋 Announcement  &  💼 Company

- **`[POST]` 기업 정보 등록**
    
    
    | 요구사항 ID | **RECRUITER_PAGE_003** |
    | --- | --- |
    | Description | 채용담당자는 공고 상세 조회에서 쓰이는 기업 정보를 등록한다. |
    | URL | /api/company/create |
    | Auth Required | Yes |
    
    | **`body params (form-data)`**  | **`요구 사항 상세`** |
    | --- | --- |
    | **[form-data]**
    dto: {
       "industry" : "중견기업",
       "name" : "한화시스템",
       "type" : "중견기업",
       "companyInfo" : "좋은 회사입니다.",
       "capital" : "50억",
       "totalEmp" : "500명",
       "establishDate" : "1999.09.09",
       "sales" : "200억",
       "business" : "방산, 우주항공 등에 관한 전반적인 it시스템 사업을 합니다.",
       "url" : "[https://www.sdlfiwjef](https://www.sdlfiwjef/)",
       "address" : "안드로메다",
       "insurance" : "사대보험 짱짱",
       "companyBenefitsList" : ["benefits_001", "benefits_002", "benefits_003"]
    }
    files: {
       image: 기업 이미지들
    }
    
     | **[채용담당자는 기업 정보를 등록]**
    1. 채용담당자별 하나의 기업만 등록 가능
    2. 등록 정보
    - 기업명
    - 기업 주소
    - 산업
    - 사원 수
    - 기업 구분
    - 설립일
    - 자본금
    - 매출액
    - 주요사업
    - 사대보험
    - 홈페이지 URL
    - 기업 소개
    - 기업 사진
    - 복리후생 |
    - **✅ Response 200**
        
        ```json
            ```json
            {
                "code": "200",
                "isSuccess": true,
                "message": "기업 정보 등록에 성공했습니다.",
                "result": {
        		        "idx" : 1,
                }
            }
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
            {
                "code": "300",
                "isSuccess": false,
                "message": "기업 정보 등록에 실패했습니다.",
                "result": {
        	         {error.details} || null
                }
            }
            ```
        ```
        
- **`[GET]` 기업 정보 조회**
    
    
    | **요구사항 ID** | **RECRUITER_PAGE_004,  ANNOUNCEMENT_009,  ANNOUNCEMENT_010** |
    | --- | --- |
    | Description | 기업 정보를 복리후생, 이미지 및 기타 정보를 조회한다. |
    | URL | /api/company/read
    /api/company/read-img
    /api/company/read-company-info |
    | Auth Required | /api/company/read = Yes
    /api/company/read-img = No
    /api/company/read-company-info = No |
    
    | **`request params`** | **`요구 사항 상세`** |
    | --- | --- |
    | /read-img: companyIdx
    /read-company-info: recruiterEmail | **[기업 정보 조회 API 들]**
    read: 기업 정보를 조회하며, 채용담당자 마이페이지에서 사용한다.
    read-company-info: 채용담당자의 공고 등록 페이지에서 복리후생 정보를 조회한다.
    read-img: 기업 이미지를 조회한다. |
    - **✅ Response 200**
        
        ```json
            ```json
            {
                "code": "200",
                "isSuccess": true,
                "message": "기업 필수정보 조회에 성공했습니다.",
                "result": {
                    "saved" : "저장 여부",
        		        "companyIdx" : "기업 idx",
        		        "crn" : "사업자등록번호",
        		        "ceoName" : "대표자명",
        		        "opendAt" : "개업일자",
        		        "industry" : "산업",
        		        "name" : "기업명",
        		        "type" : "기업구분",
        		        "companyInfo" : "기업소개",
        		        "capital" : "자본금",
        		        "totalEmp" : "사원수",
        		        "establishDate" : "설립일",
        		        "sales" : "매출액",
        		        "business" : "주요사업",
        		        "homeUrl" : "홈페이지Url",
        		        "address" : "기업주소",
        		        "imgUrlList" : [회사 이미지 리스트],
        		        "companyBenefitsList" : [회사 복리후생 리스트]
                }
            }
            ```
        ```
        
        ```json
            ```json
            {
                "code": "200",
                "isSuccess": true,
                "message": "기업 필수정보 조회에 성공했습니다.",
                "result": {
        						"benefitsDataList": [회사 복리후생 리스트]
                }
            }
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        	```json
        	{
        	    "code": "300",
        	    "isSuccess": false,
        	    "message": "조회 실패",
              "result": {
                 {error.details} || null
              }
        	}
          ```
        ```
        
- **`[POST]` 공고 상세 내용 등록 STEP 1**
    
    
    | **요구사항 ID** | **ANNOUNCEMENT_001** |
    | --- | --- |
    | Description | 채용 담당자는 필수 정보를 입력해야 채용 공고를 등록할 수 있다. |
    | URL | /api/announcement/create-step1 |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | {
       "selectForm" : 0,
       "title" : "야야야야호 개발 모집",
       "jobCategoryList" : "IT·개발 > 프론트엔드개발, 기획·전략 > 경영기획",
       "jobTitle" : "백엔드 모빌리티 플랫폼 설계 및 개발자",
       "recruitedNum" : 20,
       "careerBase" : "신입",
       "intro" : "좋은 회사입니다.",
       "positionQuali" : "자바, 스프링 필수",
       "jobType" : "정규직, 수습기간 3개월",
       "salary" : "연봉 40,000,000",
       "conditions" : "근무조건 딱히 없습니다.",
       "region" : "성남시",
       "benefits" : "유급휴가 1달",
       "managerName" : "test",
       "managerContact" : "010-1234-1234",
       "managerEmail" : “recruiter@recruiter.com",
       "process" : "서류전형, 면접전형 > 1차, 최종 합격",
       "announcementStart" : "2024-09-11 14:00:00",
       "announcementEnd" : "2024-09-17 14:00:00",
       "interviewNum" : 1,
       "note" : "유의사항은 고양이 데리고 오기"
    } | **[채용담당자는 회사의 채용 공고를 등록]
    1.** 공고 이미지로 등록 할 경우 : select_form(1) + 공고 조회를 위한 필수 정보를 꼭 작성
    2. 공고 템플릿으로 등록 할 경우 : select_form(0) + 모든 정보를 작성
    3. 필수 입력 정보
    - 공고 제목
    - 회사 소개
    - 모집 부문
    - 상세 내용 (사용 기술, 주요 업무, 자격요건, 우대 사항, 마감일 및 근무지)
    - 복지 및 혜택
    - 채용절차 및 기타 지원 유의사항
    - +추가할 정보 |
    - **✅ Response 200**
        
        ```json
            ```json
        		{
        				"code": "200",
        				"isSuccess": true,
        				"message": "공고 등록 성공",
        				"result": {
        				  "announcement_idx": 1
        		    }
        		}
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
        		{
        		    "code": "300",
        		    "isSuccess": false,
        		    "message": "공고 등록 실패",
        		    "result": {
        		       {error.details} || null
        		    }
        		}
            ```
        ```
        
- **`[POST]` 채용담당자 공고 지원서 폼 등록 STEP 2**
    
    
    | **요구 사항 ID** | **ANNOUNCEMENT_002, ANNOUNCEMENT_003, ANNOUNCEMENT_004, BASEINFO_001** |
    | --- | --- |
    | Description | 채용 담당자는 해당 기업에 맞게 지원서 항목을 선택할 수 있다. |
    | URL | /api/announcement/create-step2 |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | {
       "announcementIdx" : 1,
       "code" :  [
          "resume_001", "resume_002", "resume_005", "resume_006",
          "resume_007", "resume_009", "resume_010", "resume_011"],
       "titleList" : [
          "본 회사에 지원하게 된 동기를 작성하세요.",
          "본인의 장단점에 대해 알려주세요.",
          "문제 해결 경험에 대해 자유롭게 서술해 주세요."],
       "chatLimitList" : [400, 500, 1000]
    } | **[지원자 폼 등록]**
    1. 각 회사에 필요한 지원서 폼을 조립식으로 선택하여 등록 (아래 항목의 선택 여부)
    2. 채용 공고 등록과 동시에 각 회사에 필요한 지원서 폼을 조립식으로 선택하여 등록
    3. 조립 폼 -> base_info 테이블 활용
    4. 지원서 폼 조립 후 마지막 단계, 자기소개서 문항 커스텀
    5. 필수 입력 정보
    - 인적사항
    - 취업우대 • 병역
    - 학력
    - 경력
    - 자격증
    - 인턴·대외활동
    - 교육이수
    - 수상
    - 해외경험
    - 어학
    - 포트폴리오
    - 자기소개서
    6. 자기소개서를 추가하여 기업별로 받고싶은 문항을 입력한다
    - resume_011 코드를 조립에 추가하면 자기소개서 문항을 작성할 수 있음
    - 자기소개서 문항
    - 글자 수
    
     |
    - **✅ Response 200**
        
        ```json
            ```json
        		{
        				"code": "200",
        				"isSuccess": true,
        				"message": "공고의 지원서 폼을 등록하는데 성공했습니다.",
        				"result": {
        				  "announcement_idx": 1
        				  "baseInfoIdxList" : [1, 2, 3, 4, 9, 10, 11]
        				  "customFormLetterIdList" : [40, 41, 42]
        				  "descriptionList" : ["학력", "경력", "인턴&대외활동", "교육이수", "포트폴리오", "취업우대&병역", "자기소개서"]
        		    }
        		}
            ```
        ```
        
    - ❌ **error Response  300**
        
        ```json
            ```json
        		{
        		    "code": "300",
        		    "isSuccess": false,
        		    "message": "공고의 지원서 폼을 등록하는데 실패했습니다.",
                "result": {
        	         {error.details} || null
        	      }
        		}
            ```
        ```
        
- **`[GET]` 채용담당자 등록 공고 목록 조회 (공고 관리)**
    
    
    | 요구 사항 ID | **ANNOUNCEMENT_011, RECRUITER_PAGE_005** |
    | --- | --- |
    | Description | 채용담당자가 등록한 공고 목록 조회 |
    | URL | /api/announcement/recruiter/read-all |
    | Auth Required | Yes |
    
    | **`request params`** | **`요구 사항 상세`** |
    | --- | --- |
    | careerBase, pageNum | **[채용담당자 본인이 작성한 공고 전체 조회]**
    1. 로그인 된 채용담당자의 recruiterIdx로 본인이 작성한 공고를 전체 조회 한다.
    2. 표시 정보
    - 기업명
    - 모집시작
    - 모집마감
    - 공고 제목
    - 직무 카테고리
    - 근무지역
    - 기업사진(첫번째) |
    - **✅ Response 200**
        
        ```json
            ```json
        		{
        				"code": "200",
        				"isSuccess": true,
        				"message": "공고 목록 조회 성공",
        				"result": {
        					[
        						{
        							"announcement_idx": 1,
        							"company_name": "바이오디자인랩(주)"
        							"announcement_start": "2024-09-05",
        							"announcement_end": "2024-09-25",
        							"announcement_title": "시스템엔지니어 경력",
        							"announcement_job_category" : "개발·데이터",
        							"announcement_region": "서울",
        							"company_img_url": "https://aws.s3/2024/09/04/img_1_1.png"
        						},
        						{
        							"announcement_idx": 2,
        							"company_name": "Upstage"
        							"announcement_start": "2024-09-13",
        							"announcement_end": "2024-09-25",
        							"announcement_title": "AI DevOps",
        							"announcement_job_category" : "개발·데이터",
        							"announcement_region": "경기",
        							"company_img_url": "https://aws.s3/2024/09/04/img_2_1.png"
        						},
        						
        					]
        				  
        		    }
        		}
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
        		{
        		    "code": "300",
        		    "isSuccess": false,
        		    "message": "조회 실패",
        		    "result": error.details
        		}
            ```
        ```
        
- **`[GET]` 채용담당자 등록 공고 목록 조회 (지원서 관리)**
    
    
    | 요구 사항 ID | **ANNOUNCEMENT_011, RECRUITER_PAGE_005** |
    | --- | --- |
    | Description | 채용담당자가 등록한 공고 목록 조회 |
    | URL | /api/announcement/recruiter/read-all/resume |
    | Auth Required | Yes |
    
    | **`request params`** | **`요구 사항 상세`** |
    | --- | --- |
    | page, size | **[채용담당자 본인이 작성한 공고 전체 조회]**
    1. 로그인 된 채용담당자의 recruiterIdx로 본인이 작성한 공고를 전체 조회 한다.
    2. 공고에 지원한 지원자 수 조회
    3. 페이징 처리
    4. 표시 정보
    - 기업명
    - 모집시작
    - 모집마감
    - 공고 제목
    - 직무 카테고리
    - 근무지역
    - 기업사진(첫번째) |
    - **✅ Response 200**
        
        ```json
            ```json
        		"success": true,
        		    "code": 3200,
        		    "message": "공고 전체조회에 성공했습니다.",
        		    "result": {
        		        "content": [
        		            {
        		                "announcementIdx": 511,
        		                "announcementTitle": "삼성화재",
        		                "announcementStart": "2024-10-27 07:00:00",
        		                "announcementEnd": "2024-11-27 18:00:00",
        		                "careerBase": "신입",
        		                "seekerNum": 0
        		            },
        		            {
        		                "announcementIdx": 510,
        		                "announcementTitle": "삼성증권 상반기 경력 모집",
        		                "announcementStart": "2024-10-24 07:00:00",
        		                "announcementEnd": "2024-10-24 24:00:00",
        		                "careerBase": "경력",
        		                "seekerNum": 1
        		            },
        		            {
        		                "announcementIdx": 509,
        		                "announcementTitle": "삼성증권 하반기 경력 채용",
        		                "announcementStart": "2024-10-23 07:00:00",
        		                "announcementEnd": "2024-11-23 18:00:00",
        		                "careerBase": "경력",
        		                "seekerNum": 1
        		            },
        		            {
        		                "announcementIdx": 508,
        		                "announcementTitle": "삼성증권 경력직 채용 모집",
        		                "announcementStart": "2024-10-23 07:00:00",
        		                "announcementEnd": "2024-11-23 18:00:00",
        		                "careerBase": "경력",
        		                "seekerNum": 1
        		            },
        		            {
        		                "announcementIdx": 507,
        		                "announcementTitle": "삼성증권 신입사원 채용 공고",
        		                "announcementStart": "2024-10-22 07:00:00",
        		                "announcementEnd": "2024-11-22 18:00:00",
        		                "careerBase": "신입",
        		                "seekerNum": 4
        		            }
        		        ],
        		        "pageable": {
        		            "pageNumber": 0,
        		            "pageSize": 10,
        		            "sort": [],
        		            "offset": 0,
        		            "paged": true,
        		            "unpaged": false
        		        },
        		        "totalElements": 5,
        		        "totalPages": 1,
        		        "last": true,
        		        "size": 10,
        		        "number": 0,
        		        "sort": [],
        		        "numberOfElements": 5,
        		        "first": true,
        		        "empty": false
        		    }
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
        		{
        		    "code": "300",
        		    "isSuccess": false,
        		    "message": "공고 전체 조회에 실패했습니다.",
        		    "result": error.details
        		}
            ```
        ```
        
- **`[GET]` 지원자 공고 전체 목록 조회**
    
    
    | 요구 사항 ID | **ANNOUNCEMENT_007** |
    | --- | --- |
    | Description | 지원자가 등록된 공고 목록을 조회한다. |
    | URL | /api/announcement/read-all/see |
    | Auth Required | No |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    |  | **[지원자는 메인에서 등록된 공고 목록을 조회]**
    1. 로그인한 지원자 또는 일반 서비스 이용자가 여러 회사의 공고를 전체 조회
    - 공고 idx로 조회
    - 페이징처리
    - 리스트로 출력
    2. 필요 정보
    - 공고 idx
    - 기업명
    - 공고명
    - 모집분야명
    - 신입경력
    - 근무지역
    - 마감일”
    3. 표시 정보
    - 기업명
    - 모집시작
    - 모집마감
    - 공고 제목
    - 직무 카테고리
    - 근무지역
    - 기업사진(첫번째) |
    - **✅ Response 200**
        
        ```json
            ```json
        		{
        		    "success": true,
        		    "code": 200,
        		    "message": "공고 조회에 성공했습니다.",
        		    "result": [
        		        {
        		            "announcementIdx": 1,
        		            "companyName": "SK하이닉스",
        		            "announcementTitle": "대기업 백엔드 채용 공고",
        		            "jobTitle": "백엔드 담당자 채용",
        		            "careerBase": "경력",
        		            "region": "천안",
        		            "announcementEnd": "2024-12-09 06:04:28",
        		            "companyInfo": "메모리 반도체를 전문으로 하며, 기술 혁신으로 글로벌 반도체 시장을 선도합니다.",
        		            "imgList": [
        		                "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/59fdde58-d0a1-489b-9c8b-9b2d2f32bd2c_sk%ED%95%98%EC%9D%B4%EB%8B%89%EC%8A%A4%EB%A1%9C%EA%B3%A0.jpg",
        		                "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/6e8c7e7d-f271-4225-a503-34fc76bf5058_sk%ED%95%98%EC%9D%B4%EB%8B%89%EC%8A%A4.jpg"
        		            ]
        		        },
        		        {
        		            "announcementIdx": 2,
        		            "companyName": "SK하이닉스",
        		            "announcementTitle": "대기업 인사기획 채용 공고",
        		            "jobTitle": "인사기획 담당자 채용",
        		            "careerBase": "경력",
        		            "region": "서울",
        		            "announcementEnd": "2024-11-09 06:04:28",
        		            "companyInfo": "메모리 반도체를 전문으로 하며, 기술 혁신으로 글로벌 반도체 시장을 선도합니다.",
        		            "imgList": [
        		                "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/59fdde58-d0a1-489b-9c8b-9b2d2f32bd2c_sk%ED%95%98%EC%9D%B4%EB%8B%89%EC%8A%A4%EB%A1%9C%EA%B3%A0.jpg",
        		                "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/6e8c7e7d-f271-4225-a503-34fc76bf5058_sk%ED%95%98%EC%9D%B4%EB%8B%89%EC%8A%A4.jpg"
        		            ]
        		        },
        		        ...
        		    ]
        		}
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
        		{
        		    "code": "300",
        		    "isSuccess": false,
        		    "message": "공고 조회에 실패했습니다.",
                "result": {
        	         {error.details} || null
        	      }
        		}
            ```
        ```
        
- **`[GET]` 지원자 공고 상세 조회**
    
    
    | **요구사항 ID** | **ANNOUNCEMENT_008** |
    | --- | --- |
    | Description | 지원자가 등록된 공고를 선택하여 상세하게 조회한다. |
    | URL | /api/announcement/read-detail/see |
    | Auth Required | No |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | announcementIdx | **[지원자가 등록된 공고를 선택해 상세 조회]**
    1. 표시 정보
    - 공고제목
    - 모집시작
    - 모집마감
    - 직무 카테고리
    - 회사 소개
    - 포지션 및 자격요건
    - 근무 조건
    - 근무 지역
    - 복지 및 혜택
    - 전형절차
    - 유의사항
    - 공고 이미지
    - 기업명
    2. 로그인 한 지원자의 회원 idx와 공고 idx를 사용하여 지원서 테이블에서 해당 공고에 지원했는지 여부를 받아와야 한다. (is_resume)
    - 지원 여부에 따라 공고 마감 기한이 지나지 않았을 때
    지원 o → 지원서 수정
    지원 x → 지원하기
    버튼 노출 |
    - **✅ Response 200**
        
        ```json
            ```json
        		{
        		    "success": true,
        		    "code": 200,
        		    "message": "공고 조회에 성공했습니다.",
        		    "result": {
        		        "announcementIdx": 1,
        		        "companyIdx": 1,
        		        "title": "대기업 백엔드 채용 공고",
        		        "selectForm": false,
        		        "imgUrl": null,
        		        "jobCategory": "재무&회계 > 세무",
        		        "jobTitle": "백엔드 담당자 채용",
        		        "recruitedNum": 2,
        		        "careerBase": "경력",
        		        "positionQuali": "주요 업무 : 백엔드 관리 및 유지보수 // 근무 부서 : IT팀 // 직급 직책 : 사원 // 필수, 우대 조건 : 관련 분야 경력 3년 이상",
        		        "region": "천안",
        		        "jobType": "정규직",
        		        "salary": "연봉 : 7142만원",
        		        "conditions": "근무 시간 : 40시간(주) // 근무 요일 : 주 5일(월~금) // 출퇴근 시간 : 09시 ~ 18시",
        		        "benefits": "재택근무 장려",
        		        "managerName": "recruiter1",
        		        "managerContact": "비공개",
        		        "managerEmail": "recruiter1@test.com",
        		        "intro": "우리 회사는 IT 분야에서 최고의 성과를 내고 있는 기업입니다. 저희와 함께 성장하실 인재를 찾습니다.",
        		        "announcementStart": "2024-10-10 06:04:28",
        		        "announcementEnd": "2024-12-09 06:04:28",
        		        "interviewNum": 2,
        		        "process": "서류전형, 면접전형 > 1차, 면접전형 > 2차, 최종합격",
        		        "deadlineDocument": "2024-10-17",
        		        "deadlineFinal": "2024-10-27",
        		        "note": "ㆍ허위사실이 발견될 경우 채용이 취소될 수 있습니다.\n\nㆍ공통 응시자격: 해외 여행에 결격사유가 없으신 분, 방산 보안 정책상 결격 사유가 없는 분, 남성일 경우 병역의무를 마치셨거나 면제되신 분",
        		        "companyIndustry": "반도체",
        		        "companyName": "SK하이닉스",
        		        "companyType": "대기업",
        		        "companyInfo": "메모리 반도체를 전문으로 하며, 기술 혁신으로 글로벌 반도체 시장을 선도합니다.",
        		        "companyCapital": "366억",
        		        "companyTotalEmp": "29040",
        		        "companyEstablishDate": "1983-02-08",
        		        "companySales": "42900000000000",
        		        "companyBusiness": "반도체 제조 및 공급",
        		        "companyUrl": "https://www.skhynix.com",
        		        "companyAddress": "경기도 이천시 부발읍 경충대로 2091",
        		        "companyBenefitsDataList": [
        		            {
        		                "category": "휴무&휴가&행사",
        		                "subcategories": [
        		                    "연차제도",
        		                    "자유로운 휴가문화"
        		                ]
        		            },
        		            {
        		                "category": "연금&보험",
        		                "subcategories": [
        		                    "국민연금",
        		                    "고용보험",
        		                    "건강보험"
        		                ]
        		            },
        		            {
        		                "category": "편의&여가&건강",
        		                "subcategories": [
        		                    "출퇴근 셔틀버스"
        		                ]
        		            },
        		            {
        		                "category": "사내제도&성장",
        		                "subcategories": [
        		                    "신입 사원 교육",
        		                    "님/닉네임 호칭",
        		                    "수평적 문화"
        		                ]
        		            },
        		            {
        		                "category": "보상&수당&지원",
        		                "subcategories": [
        		                    "경조금",
        		                    "복지포인트",
        		                    "휴일 수당",
        		                    "성과급"
        		                ]
        		            },
        		            {
        		                "category": "근무환경",
        		                "subcategories": [
        		                    "사무용품 지급",
        		                    "노트북 지원",
        		                    "비상경보장치",
        		                    "카페테리아",
        		                    "공기청정기"
        		                ]
        		            }
        		        ],
        		        "imgList": null
        		    }
        		}
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
        		{
        		    "code": "300",
        		    "isSuccess": false,
        		    "message": "공고 조회에 실패했습니다.",
        		    "result": {
        		       {error.details} || null
        		    }
        		}
            ```
        ```
        
- **`[GET]` 지원자 공고 카테고리 검색 조회**
    
    
    | Description | 지원자는 공고 전체 조회의 검색바에서 3개의 카테고리로 필터를 추가해 공고 검색 할 수 있다. |
    | --- | --- |
    | URL | /api/announcement/announcement |
    | Auth Required | No |
    | 요구사항 ID | SEARCH_001 |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | {
       page: 0
       size: 24
       keyword: “신한”
       careerBase: “신입”
       jobCategory: “교육”
       region: “재택근무가능’
       sort: “마감임박순”
    } | **[카테고리 검색 기능]**
    1. 검색어
    2. 채용형태
    3. 모집직무
    4. 근무지역
    5. 전체, 마감 임박순, 최신 등록순 |
    - **✅ Response 200**
        
        ```json
        ```json
        {
            "success": true,
            "code": 3300,
            "message": "공고 조회에 성공했습니다.",
            "result": {
                "content": [
                    {
                        "announcementIdx": 4,
                        "companyName": "SK하이닉스",
                        "announcementTitle": "대기업 디자인 채용 공고",
                        "jobTitle": "디자인 담당자 채용",
                        "careerBase": "신입",
                        "region": "천안",
                        "announcementEnd": "2024-10-31 06:04:28",
                        "companyInfo": "메모리 반도체를 전문으로 하며, 기술 혁신으로 글로벌 반도체 시장을 선도합니다.",
                        "imgList": [
                            "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/59fdde58-d0a1-489b-9c8b-9b2d2f32bd2c_sk%ED%95%98%EC%9D%B4%EB%8B%89%EC%8A%A4%EB%A1%9C%EA%B3%A0.jpg",
                            "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/6e8c7e7d-f271-4225-a503-34fc76bf5058_sk%ED%95%98%EC%9D%B4%EB%8B%89%EC%8A%A4.jpg"
                        ]
                    },
                    {
                        "announcementIdx": 8,
                        "companyName": "SK하이닉스",
                        "announcementTitle": "대기업 프론트엔드 채용 공고",
                        "jobTitle": "프론트엔드 담당자 채용",
                        "careerBase": "신입",
                        "region": "울산",
                        "announcementEnd": "2024-10-31 06:04:28",
                        "companyInfo": "메모리 반도체를 전문으로 하며, 기술 혁신으로 글로벌 반도체 시장을 선도합니다.",
                        "imgList": [
                            "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/59fdde58-d0a1-489b-9c8b-9b2d2f32bd2c_sk%ED%95%98%EC%9D%B4%EB%8B%89%EC%8A%A4%EB%A1%9C%EA%B3%A0.jpg",
                            "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/6e8c7e7d-f271-4225-a503-34fc76bf5058_sk%ED%95%98%EC%9D%B4%EB%8B%89%EC%8A%A4.jpg"
                        ]
                    },
        						...	
                    {
                        "announcementIdx": 86,
                        "companyName": "NH투자증권",
                        "announcementTitle": "선도기업 프론트엔드 채용 공고",
                        "jobTitle": "프론트엔드 담당자 채용",
                        "careerBase": "신입",
                        "region": "전주",
                        "announcementEnd": "2024-12-09 06:04:28",
                        "companyInfo": "증권 및 자산 관리를 통해 안정적인 재테크 솔루션을 제공합니다.",
                        "imgList": [
                            "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/4ba0e07e-8e08-42c7-8816-bd51befe468f_nh%ED%88%AC%EC%9E%90%EC%A6%9D%EA%B6%8C%EB%A1%9C%EA%B3%A0.png",
                            "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/5482fa21-446c-45c5-aa72-6eb4ef8eb924_nh%ED%88%AC%EC%9E%90%EC%A6%9D%EA%B6%8C.jpg"
                        ]
                    }
                ],
                "pageable": {
                    "pageNumber": 0,
                    "pageSize": 24,
                    "sort": [],
                    "offset": 0,
                    "paged": true,
                    "unpaged": false
                },
                "totalElements": 177,
                "totalPages": 8,
                "last": false,
                "size": 24,
                "number": 0,
                "sort": [],
                "numberOfElements": 24,
                "first": true,
                "empty": false
            }
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": "300",
        	"isSuccess": false,
        	"message": "공고 조회에 실패했습니다.",
          "result": {
             {error.details} || null
          }
        }
        ```
        ```
        

---

# 💬 Resume

- **`[POST]` 통합 지원서 등록**
    
    
    | **요구사항 ID** | **RESUME_001, RESUME_002, RESUME_003, RESUME_004, RESUME_005, RESUME_006** |
    | --- | --- |
    | Description | 지원자는 공고에 지원시 자동으로 입력한 값을 불러오기 위해 통합지원서를 작성한다. |
    | URL | /api/resume/create |
    | Auth Required | Yes |
    
    | **`body params (form-data)`** | **`요구 사항 상세`** |
    | --- | --- |
    | dto: {
       "codes": [ "resume_010", "resume001", "resume_002"],
       "personalInfo": {
       "name":"seeker1",
       "birth":"2024-07-05",
       "gender":"여자",
       "[email":"wodms5522@naver.com](mailto:email%22:%22wodms5522@naver.com)",
       "phone":"01087924308",
       "address":"경기 안양시"
    },
    "preferentialEmp": {
       "veterans":0,
       "protection":0,
       "subsidy":0,
       "disability":0,
       "military":1,
       "military_class":"군필",
       "military_start":"2024-01-01",
       "military_end":"2024-07-05",
       "military_type":"육군",
       "military_rank":"이병"
    },
    "educations": [
       {
          "high_less":0,
          "school_div":"대학교(4년)",
          "school_name":"서울대학교",
          "entered_at":"2019.03",
          "graduated_at":"2023.02",
          "graduation_status":"졸업",
          "major_name":"컴퓨터공학과",
          "grade":4.5,
          "total_grade":4.5,
          "transfer":0,
          "qualification_exam":0
       }
    ],
    "personalHistories": [
       {
          "company_name":"삼성 SDS",
          "dept_name":"인사부",
          "entered_at":"2024.02",
          "quit_at":"2024.03",
          "emp_status":0,
          "rank":"대리",
          "position":"매니저",
          "job":"백엔드",
          "salary":5000
       },
    {
       "company_name": "ABC 회사",
       "dept_name": "개발팀",
       "entered_at": "2013-03-01",
       "quit_at": "2018-12-31",
       "emp_status": false,
       "position": "개발자",
       "job": "백엔드 개발",
       "salary": 50000000,
       "work": "백엔드 시스템 개발 및 유지보수"
    }
    ]
    }
    file: 포트폴리오, 이력서 등의 문서 | **[공고에 지원시 자동 입력을 위한 통합지원서]**
    1. 인적사항
    - 이름
    - 생년월일
    - 성별
    - 이메일
    - 휴대폰번호
    - 주소
    2. 취업우대 • 병역
    - 보훈대상 여부
    - 취업보호대상 여부
    - 고용지원금대상 여부
    - 장애 여부
    - 장애 등급
    - 병역 여부
    - 병역 : 군필, 미필, 면제, 해당없음"
    3. 학력
    - 학교구분 : 고등학교, 대학교(2,3년), 대학교(4년), 대학원
    - 학교명
    - 입학년월
    - 졸업년월
    - 졸업상태
    - 전공명
    - 학점/총점
    4. 경력
    - 회사명
    - 부서명
    - 입사년월
    - 퇴사년월
    - 재직중 여부
    - 직급
    - 담당직무
    - 연봉
    - 담당업무"
    5. 인턴·대외활동
    - 활동구분
    - 회사/기관/단체명
    - 시작년월
    - 종료년월
    - 활동내용
    6. 해외경험
    - 국가명
    - 시작년월
    - 종료년월
    - 내용
    7. 어학
    - 구분 : 회화능력, 공인시험
    - 외국어명 : 영어, 일본어, ...
    - 회화능력 : 일상회화 가능, 비즈니스 회화가능, 원어민 수준"
    8. 자격증
    - 자격증명
    - 발행기관
    - 취득월
    
    9. 교육이수
    - 교육명
    - 교육기관
    - 시작일
    - 종료일
    - 내용
    
    10. 수상
    - 수상명
    - 수여기관
    - 수상연도
    - 수여내용"
    11. 포트폴리오
    12. 자기소개서: 채용담당자가 공고를 등록할 때 커스텀한 항목대로 작성" |
    - **✅ Response 200**
        
        ```json
            ```json
        		{
        				"code": "200",
        				"isSuccess": true,
        				"message": "지원서 등록에 성공했습니다.",
        				"result": {
        				  "resumeInfoIdx": 1,
        		    }
        		}
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
        		{
        		    "code": "300",
        		    "isSuccess": false,
        		    "message": "지원서 등록에 실패했습니다.",
        			  "result": {
        			     {error.details} || null
        			  }
        		}
            ```
        ```
        
- **`[GET]` 통합 지원서 상세 조회**
    
    
    | **요구사항 ID** | **MEMBER_PAGE_004** |
    | --- | --- |
    | Description | 지원자는 공고 상세에서 지원하기 버튼 클릭 시 마이페이지에서 등록한 통합 지원서 조회 가능 |
    | URL | /api/resume/integrated |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | X | **[통합 지원서 조회]**
    1. 표시 정보
    - 인적사항
    - 취업우대 • 병역
    - 학력
    - 경력
    - 인턴·대외활동
    - 해외경험
    - 어학
    - 자격증
    - 교육이수
    - 수상
    - 포트폴리오
    - 자기소개서: 채용담당자가 공고를 등록할 때 커스텀한 항목대로 작성 |
    - **✅ Response 200**
        
        ```json
            ```json
        		{
            "success": true,
            "code": 2100,
            "message": "지원서 조회에 성공하였습니다.",
            "result": {
                "codes": [
                    "resume_001",
                    "resume_002",
                    "resume_003",
                    "resume_004",
                    "resume_005",
                    "resume_006",
                    "resume_007",
                    "resume_008",
                    "resume_009",
                    "resume_010"
                ],
                "personalInfo": {
                    "name": "지원자1",
                    "birth": "2000.10.01",
                    "gender": "남자",
                    "email": "okqkrwhdtjd@naver.com",
                    "phone": "010-1234-1234",
                    "tel": null,
                    "address": "서울 동대문구 서울시립대로 5 ",
                    "profileImg": "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/08dcba1b-3b8c-4010-bdc2-a5a0ac99629f_adcda5d1ce294e6d9d84d73bf77ffe2f.jpeg"
                },
                "preferentialEmp": {
                    "veterans": false,
                    "protection": false,
                    "subsidy": false,
                    "disability": false,
                    "disabilityDegree": "",
                    "military": true,
                    "militaryClass": "군필",
                    "militaryStart": null,
                    "militaryEnd": null,
                    "militaryType": "육군",
                    "militaryRank": "병장"
                },
                "educations": [
                    {
                        "schoolDiv": "고등학교",
                        "schoolName": "고등어학교",
                        "enteredAt": null,
                        "graduatedAt": "2016.02",
                        "graduationStatus": "졸업",
                        "majorName": null,
                        "grade": null,
                        "totalGrade": null,
                        "transfer": false,
                        "majorType": "",
                        "otherMajor": null,
                        "graduationWork": null,
                        "degree": "",
                        "qualificationExam": false,
                        "passedAt": null
                    }
                ],
                "personalHistories": [
                    {
                        "companyName": "경력회사",
                        "deptName": "it",
                        "enteredAt": "2023.03",
                        "quitAt": "2023.03",
                        "empStatus": false,
                        "position": "사원",
                        "job": "개발",
                        "salary": 10000,
                        "work": "열심히 다녔습니다."
                    }
                ],
                "internsActivities": [
                    {
                        "activityDiv": "인턴",
                        "organization": "삼성 증권 ",
                        "startAt": "2023.03",
                        "endAt": "2023.03",
                        "contents": "열심히 다녔습니다."
                    }
                ],
                "studyingAbroads": [
                    {
                        "countryName": "일본",
                        "startAt": "2023.03",
                        "endAt": "2023.03",
                        "contents": "어학연수"
                    }
                ],
                "languages": [
                    {
                        "testDiv": "회화능력",
                        "languageName": "일본어",
                        "conversationLevel": "원어민 수준",
                        "officialTest": null,
                        "score": null,
                        "takingAt": null
                    }
                ],
                "certifications": [
                    {
                        "certName": "정보처리기사",
                        "organization": "삼성 증권 ",
                        "takingAt": "2023.03"
                    }
                ],
                "trainings": [
                    {
                        "trainingName": "한화시스템 Beyond SW 캠프",
                        "organization": "삼성 증권 ",
                        "startAt": "2023.03",
                        "endAt": "2023.03",
                        "contents": "열심히 다녔습니다."
                    }
                ],
                "awards": [
                    {
                        "awardName": "정보처리상",
                        "contents": "정보처리상을 받았습니다.",
                        "organization": "삼성 증권 ",
                        "year": "2023"
                    }
                ],
                "customLetters": null,
                "portfolios": [
                    {
                        "portfolioDiv": "포트폴리오",
                        "portfolioType": "파일",
                        "portfolioUrl": "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/b8690004-975a-40e1-b77d-8a732e3fbeb1_7a3ea342078a5ea9.webp"
                    }
                ]
        	    }
        		}
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
        		{
        		    "code": "300",
        		    "isSuccess": false,
        		    "message": "지원서 조회에 실패했습니다.",
        			  "result": {
        			     {error.details} || null
        			  }
        		}
            ```
        ```
        
- **`[GET]` 공고 지원시 통합 지원서 조회**
    
    
    | **요구사항 ID** | **RESUME_012** |
    | --- | --- |
    | Description | 지원자는 공고 상세에서 지원하기 버튼 클릭 시 마이페이지에서 등록한 통합 지원서 조회 가능 |
    | URL | /api/resume/submit-info |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | announcementIdx | **[공고 지원시 통합 지원서 조회]**
    1. 지원자는 공고 상세에서 지원하기 버튼 클릭 시 마이페이지에서 등록한 통합 지원서 조회 가능
    2. 단, 공고를 등록한 기업에서 선택한 항목에 대해서만 조회 가능
    3. 표시 정보
    - 인적사항
    - 취업우대 • 병역
    - 학력
    - 경력
    - 인턴·대외활동
    - 해외경험
    - 어학
    - 자격증
    - 교육이수
    - 수상
    - 포트폴리오
    - 자기소개서: 채용담당자가 공고를 등록할 때 커스텀한 항목대로 작성 |
    - **✅ Response 200**
        
        ```json
            ```json
        		{
        		    "success": true,
        		    "code": 200,
        		    "message": "지원서 조회에 성공하였습니다.",
        		    "result": {
        		        "codes": [
        		            "resume_001",
        		            "resume_002",
        		            "resume_003",
        		            "resume_004",
        		            "resume_005",
        		            "resume_006",
        		            "resume_007",
        		            "resume_008",
        		            "resume_009",
        		            "resume_010",
        		            "resume_011"
        		        ],
        		        "integrated": true,
        		        "personalInfo": {
        		            "name": "지원자1",
        		            "birth": "2000.10.01",
        		            "gender": "남자",
        		            "email": "okqkrwhdtjd@naver.com",
        		            "phone": "010-1234-1234",
        		            "tel": null,
        		            "address": "서울 동대문구 서울시립대로 5 ",
        		            "profileImg": "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/08dcba1b-3b8c-4010-bdc2-a5a0ac99629f_adcda5d1ce294e6d9d84d73bf77ffe2f.jpeg"
        		        },
        		        "preferentialEmp": {
        		            "veterans": false,
        		            "protection": false,
        		            "subsidy": false,
        		            "disability": false,
        		            "disabilityDegree": "",
        		            "military": true,
        		            "militaryClass": "군필",
        		            "militaryStart": null,
        		            "militaryEnd": null,
        		            "militaryType": "육군",
        		            "militaryRank": "병장"
        		        },
        		        "educations": [
        		            {
        		                "schoolDiv": "고등학교",
        		                "schoolName": "고등어학교",
        		                "enteredAt": null,
        		                "graduatedAt": "2016.02",
        		                "graduationStatus": "졸업",
        		                "majorName": null,
        		                "grade": null,
        		                "totalGrade": null,
        		                "transfer": false,
        		                "majorType": "",
        		                "otherMajor": null,
        		                "graduationWork": null,
        		                "degree": "",
        		                "qualificationExam": false,
        		                "passedAt": null
        		            }
        		        ],
        		        "personalHistories": [
        		            {
        		                "companyName": "경력회사",
        		                "deptName": "it",
        		                "enteredAt": "2023.03",
        		                "quitAt": "2023.03",
        		                "empStatus": false,
        		                "position": "사원",
        		                "job": "개발",
        		                "salary": 10000,
        		                "work": "열심히 다녔습니다."
        		            }
        		        ],
        		        "internsActivities": [
        		            {
        		                "activityDiv": "인턴",
        		                "organization": "삼성 증권 ",
        		                "startAt": "2023.03",
        		                "endAt": "2023.03",
        		                "contents": "열심히 다녔습니다."
        		            }
        		        ],
        		        "studyingAbroads": [
        		            {
        		                "countryName": "일본",
        		                "startAt": "2023.03",
        		                "endAt": "2023.03",
        		                "contents": "어학연수"
        		            }
        		        ],
        		        "languages": [
        		            {
        		                "testDiv": "회화능력",
        		                "languageName": "일본어",
        		                "conversationLevel": "원어민 수준",
        		                "officialTest": null,
        		                "score": null,
        		                "takingAt": null
        		            }
        		        ],
        		        "certifications": [
        		            {
        		                "certName": "정보처리기사",
        		                "organization": "삼성 증권 ",
        		                "takingAt": "2023.03"
        		            }
        		        ],
        		        "trainings": [
        		            {
        		                "trainingName": "한화시스템 Beyond SW 캠프",
        		                "organization": "삼성 증권 ",
        		                "startAt": "2023.03",
        		                "endAt": "2023.03",
        		                "contents": "열심히 다녔습니다."
        		            }
        		        ],
        		        "awards": [
        		            {
        		                "awardName": "정보처리상",
        		                "contents": "정보처리상을 받았습니다.",
        		                "organization": "삼성 증권 ",
        		                "year": "2023"
        		            }
        		        ],
        		        "customLetterForms": [
        		            {
        		                "title": "본 회사에 지원하게 된 동기를 작성하세요.",
        		                "chatLimit": 400
        		            },
        		            {
        		                "title": "본인의 장단점에 대해 알려주세요.",
        		                "chatLimit": 500
        		            },
        		            {
        		                "title": "문제 해결 경험에 대해 자유롭게 서술해 주세요.",
        		                "chatLimit": 1000
        		            }
        		        ],
        		        "portfolios": null
        		    }
        		}
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
        		{
        		    "code": "300",
        		    "isSuccess": false,
        		    "message": "지원서 조회에 실패했습니다.",
        			  "result": {
        			     {error.details} || null
        			  }
        		}
            ```
        ```
        
- **`[POST]` 공고 지원서 등록**
    
    
    | **요구사항 ID** | **RESUME_001, RESUME_002, RESUME_003, RESUME_004, RESUME_005, RESUME_006** |
    | --- | --- |
    | Description | 지원자는 기업에서 커스텀 해 준 폼대로 지원서를 작성한다. |
    | URL | /api/resume/submit |
    | Auth Required | Yes |
    
    | **`body params (form-data)`** | **`요구 사항 상세`** |
    | --- | --- |
    | {
        "announcementIdx":1,
        "resumeTitle": "한화시스템 지원서",
        "codes": ["resume_001", "resume_002", "resume_005", "resume_006", "resume_007", "resume_009", "resume_010", "resume_011"],
        "personalInfo": {
            "name": "seeker1",
            "birth": "2024-07-05",
            "gender": "여자",
            "email": "seeker1@seeker.com",
            "phone": "01087924308",
            "address": "경기 안양시 동안구"
        },
        "preferentialEmp": {
            "veterans": 0,
            "protection": 0,
            "subsidy": 0,
            "disability": 0,
            "military": 1,
            "militaryClass": "해당없음"
        },
        "educations": [
            {
                "highLess": 0,
                "schoolDiv": "대학교(4년)",
                "schoolName": "하버드대학교",
                "enteredAt": "2019.03",
                "graduatedAt": "2023.02",
                "graduationStatus": "졸업",
                "majorName": "영어영문학과",
                "grade": 4.5,
                "totalGrade": 4.5,
                "transfer": 0,
                "qualificationExam": 0
            }
        ],
        "personalHistories": [
            {
                "companyName": "한화시스템",
                "deptName": "개발부",
                "enteredAt": "2024.02",
                "quitAt": "2024.03",
                "empStatus": 0,
                "rank": "부장",
                "position": "매니저",
                "job": "프론트엔드",
                "salary": 500000000
            },
            {
                "companyName": "ABC 회사",
                "deptName": "개발팀",
                "enteredAt": "2013-03-01",
                "quitAt": "2018-12-31",
                "empStatus": false,
                "position": "개발자",
                "job": "백엔드 개발",
                "salary": 50000000,
                "work": "백엔드 시스템 개발 및 유지보수를 했습니다. 실력 레전드 입니다."
            }
        ],
        
        "studyingAbroads": [
            {
                "countryName": "미국",
                "startAt": "2023.05",
                "endAt": "2024.05",
                "contents": "해외에서 어떤 경험을 했을까요???????"
            }
        ],
        "certifications": [
            {
                "certName": "정보처리기사",
                "organization": "몰라",
                "takingAt": "2022.11"
            },
            {
                "certName": "SQLD",
                "organization": "몰라",
                "takingAt": "2024.9"
            }
        ],
        "awards": [
            {
                "awardName": "수학경시대회",
                "contents": "전국 수학경시대회 상 받음 ㅎㅎ",
                "organization": "서울대학교",
                "year": "2023"
            }
        ],
        "customLetters": [
            {
                "title": "성격의 장단점",
                "charNum": 500,
                "contents": "[안 되면 되게 하라]\n안 되면 되게 하는 끈기가 장점이라고 생각합니다. IT 회사에서 근무하며 IT 세계는 생각보다 더 넓고 보이는 것보다 더 복잡하다는 것을 깨달았습니다. 비전공자로, 용어에 익숙지 않은 결점이 업무에 있어서 약점이 되고 싶지 않았습니다. 업계 관련 키워드를 설정하여 뉴스를 읽으며 출근하였고, 용어집을 만들어 공부하였습니다. 생소했던 단어들이 들리기 시작하며 업무에 대한 이해도 또한 높아졌습니다. 부족한 지식을 채우기 위하여 벤더사에서 진행하는 시장 상황과 솔루션에 대해 소개하는 웨비나에 참여하며 지식을 쌓았고 업계 사람들과의 네트워킹을 통하여 업무 관련 인사이트를 얻었습니다. 그 결과로 솔루션 소개 브로셔 제작, 디바이스 비교 책자 제작 등 영업 활동을 지원하며 회사가 이익을 창출하는 과정을 보다 체계적이고 효율적으로 만드는 데에 이바지하였습니다.\n[자만심]\n학부 시절 그룹 과제에서 항상 리더 역할을 자처했고, 실무를 할 때도 다수의 프로젝트를 담당하며 진취적인 태도로 업무에 임한다는 칭찬을 자주 듣다 보니 자신감이 한순간에 자만심으로 바뀌었습니다. 출근 후 업무를 계획하는 루틴을 생략하게 되고, 여러 번 검토하는 습관이 무너지며 사소한 실수를 하는 일이 종종 발생하였습니다. 문제점 보완을 위해 초심으로 돌아가 메모하는 습관을 다시 들이고 시간을 배분하여 업무를 수행하는 습관을 기르기 위해 노력하였습니다."
            },
            {
                "title": "이 회사에 지원한 동기 및 포부",
                "charNum": 600,
                "contents": "저는 폐기물 업체에 큰 관심을 가지고 있습니다. 이한산업이라는 재활용기 전문업체 가치평가에 참여하기도 하였으며, 직접 경기도에 위치한 작은 음식물 쓰레기 처리업체 딜 소싱을 주선하기도 하였습니다. 이도는 수집운반, 중간처리, 소각, 에너지화, 그리고 최종 처리 시설까지 전과정의 벨류체인을 보유하고 있는 것으로 알고 있습니다. 이러한 산업의 리딩펌에서 인턴쉽을 경험하는 것은 쉽게 오지 않는 기회로 생각합니다. \n금번 인턴쉽을 통해서 친환경 산업에 대해서 배우고 커리어를 구체화하고 싶습니다. 또한 업계 최고의 전문가 분들의 일을 도우며 인사이트를 얻고 싶습니다. 본 인턴쉽은 체험형이나 열심히 업무에 임하여 팀에 기여하고 이후 다시 정규직으로 지원하고자 합니다."
            },
            {
                "title": "성장과정",
                "charNum": 400,
                "contents": "대학 시절, 저는 '대학생 자원봉사 동아리'에서 활동을 시작했습니다. 처음에는 봉사 활동에 참여하며 동아리의 일원으로서 기초적인 업무를 맡았으나, 시간이 지남에 따라 동아리의 규모가 커지고 다양한 프로젝트가 진행되면서 복잡한 업무와 문제들이 발생하게 되었습니다. 이에 대해 문제를 해결하고자 다음과 같은 조치를 취했습니다."
            }
        ],
        "portfolios": [
            {
                "portfolioDiv": "이력서",
                "portfolioType": "url",
                "portfolioUrl": "https://www.google.com/webhp?hl=ko&sa=X&ved=0ahUKEwis8v3CmbyIAxVOr1YBHRpXIq4QPAgI"
            },
            {
                "portfolioDiv": "포트폴리오",
                "portfolioType": "url",
                "portfolioUrl": "https://www.naver.com"
            }
        ]
    }
     | **[공고 지원시 지원서 입력]**
    1. 인적사항
    - 이름
    - 생년월일
    - 성별
    - 이메일
    - 휴대폰번호
    - 주소
    2. 취업우대 • 병역
    - 보훈대상 여부
    - 취업보호대상 여부
    - 고용지원금대상 여부
    - 장애 여부
    - 장애 등급
    - 병역 여부
    - 병역 : 군필, 미필, 면제, 해당없음"
    3. 학력
    - 학교구분 : 고등학교, 대학교(2,3년), 대학교(4년), 대학원
    - 학교명
    - 입학년월
    - 졸업년월
    - 졸업상태
    - 전공명
    - 학점/총점
    4. 경력
    - 회사명
    - 부서명
    - 입사년월
    - 퇴사년월
    - 재직중 여부
    - 직급
    - 담당직무
    - 연봉
    - 담당업무"
    5. 인턴·대외활동
    - 활동구분
    - 회사/기관/단체명
    - 시작년월
    - 종료년월
    - 활동내용
    6. 해외경험
    - 국가명
    - 시작년월
    - 종료년월
    - 내용
    7. 어학
    - 구분 : 회화능력, 공인시험
    - 외국어명 : 영어, 일본어, ...
    - 회화능력 : 일상회화 가능, 비즈니스 회화가능, 원어민 수준"
    8. 자격증
    - 자격증명
    - 발행기관
    - 취득월
    
    9. 교육이수
    - 교육명
    - 교육기관
    - 시작일
    - 종료일
    - 내용
    
    10. 수상
    - 수상명
    - 수여기관
    - 수상연도
    - 수여내용"
    11. 포트폴리오
    12. 자기소개서: 채용담당자가 공고를 등록할 때 커스텀한 항목대로 작성 |
    - **✅ Response 200**
        
        ```json
            ```json
        		{
        				"code": "200",
        				"isSuccess": true,
        				"message": "지원서 등록에 성공했습니다.",
        				"result": {
        				  "resumeInfoIdx": 1,
        		    }
        		}
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
        		{
        		    "code": "300",
        		    "isSuccess": false,
        		    "message": "지원서 등록에 실패했습니다.",
        			  "result": {
        			     {error.details} || null
        			  }
        		}
            ```
        ```
        
- **`[GET]` 공고 지원서 상세 조회 (지원자, 채용담당자)**
    
    
    | **요구사항 ID** | **RESUME_010, MEMBER_PAGE_003** |
    | --- | --- |
    | Description | 회원은 작성한 글 목록을 볼 수 있다. |
    | URL | /api/resume/read |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | resumeIdx | [**지원자 또는 채용담당자가 공고에 지원한 지원서를 조회]**
    1. 지원서 항목은 채용담당자가 공고를 등록할 때 조립한 지원서 폼과 자기소개서 항목을 대로 지원자가 작성한 지원서를 조회
    2. 지원자 또는 채용담당자는 resumeIdx로 지원서를 상세 조회 가능
    3. 조회 정보
    - 지원서 제목
    - 지원 날짜
    - 인적사항
    - 취업우대 • 병역
    - 학력
    - 경력
    - 인턴·대외활동
    - 자격증
    - 교육이수
    - 어학
    - 해외경험
    - 수상
    - 포트폴리오
    - 자기소개서 |
    - **✅ Response 200**
        
        ```json
            ```json
            {
        	    "success": true,
        	    "code": 2100,
        	    "message": "지원서 조회에 성공하였습니다.",
        	    "result": {
        	        "codes": [
        	            "resume_001",
        	            "resume_002",
        	            "resume_003",
        	            "resume_004",
        	            "resume_005",
        	            "resume_006",
        	            "resume_007",
        	            "resume_008",
        	            "resume_009",
        	            "resume_010",
        	            "resume_011"
        	        ],
        	        "resumeTitle": "지원자1",
        	        "resumedAt": "2024-10-22T17:28:01.735004",
        	        "docPassed": true,
        	        "seekerIdx": 328,
        	        "announcementIdx": 507,
        	        "personalInfo": {
        	            "name": "지원자1",
        	            "birth": "2000.10.01",
        	            "gender": "남자",
        	            "email": "okqkrwhdtjd@naver.com",
        	            "phone": "010-1234-1234",
        	            "tel": null,
        	            "address": "서울 동대문구 서울시립대로 5 ",
        	            "profileImg": "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/c089a6ab-6739-47ac-9778-b7fedfe6a605_adcda5d1ce294e6d9d84d73bf77ffe2f.jpeg"
        	        },
        	        "preferentialEmp": {
        	            "veterans": false,
        	            "protection": false,
        	            "subsidy": false,
        	            "disability": false,
        	            "disabilityDegree": "",
        	            "military": true,
        	            "militaryClass": "군필",
        	            "militaryStart": null,
        	            "militaryEnd": null,
        	            "militaryType": "육군",
        	            "militaryRank": "병장"
        	        },
        	        "educations": [
        	            {
        	                "schoolDiv": "고등학교",
        	                "schoolName": "고등어학교",
        	                "enteredAt": null,
        	                "graduatedAt": "2016.02",
        	                "graduationStatus": "졸업",
        	                "majorName": null,
        	                "grade": null,
        	                "totalGrade": null,
        	                "transfer": false,
        	                "majorType": "",
        	                "otherMajor": null,
        	                "graduationWork": null,
        	                "degree": "",
        	                "qualificationExam": false,
        	                "passedAt": null
        	            }
        	        ],
        	        "personalHistories": [
        	            {
        	                "companyName": "경력회사",
        	                "deptName": "it",
        	                "enteredAt": "2023.03",
        	                "quitAt": "2023.03",
        	                "empStatus": false,
        	                "position": "사원",
        	                "job": "개발",
        	                "salary": 10000,
        	                "work": "열심히 다녔습니다."
        	            }
        	        ],
        	        "internsActivities": [
        	            {
        	                "activityDiv": "인턴",
        	                "organization": "삼성 증권 ",
        	                "startAt": "2023.03",
        	                "endAt": "2023.03",
        	                "contents": "열심히 다녔습니다."
        	            }
        	        ],
        	        "studyingAbroads": [
        	            {
        	                "countryName": "일본",
        	                "startAt": "2023.03",
        	                "endAt": "2023.03",
        	                "contents": "어학연수"
        	            }
        	        ],
        	        "languages": [
        	            {
        	                "testDiv": "회화능력",
        	                "languageName": "일본어",
        	                "conversationLevel": "원어민 수준",
        	                "officialTest": null,
        	                "score": null,
        	                "takingAt": null
        	            }
        	        ],
        	        "certifications": [
        	            {
        	                "certName": "정보처리기사",
        	                "organization": "삼성 증권 ",
        	                "takingAt": "2023.03"
        	            }
        	        ],
        	        "trainings": [
        	            {
        	                "trainingName": "한화시스템 Beyond SW 캠프",
        	                "organization": "삼성 증권 ",
        	                "startAt": "2023.03",
        	                "endAt": "2023.03",
        	                "contents": "열심히 다녔습니다."
        	            }
        	        ],
        	        "awards": [
        	            {
        	                "awardName": "정보처리상",
        	                "contents": "정보처리상을 받았습니다.",
        	                "organization": "삼성 증권 ",
        	                "year": "2023"
        	            }
        	        ],
        	        "customLetters": [
        	            {
        	                "title": "지원동기",
        	                "charNum": 249,
        	                "contents": "군 부사관으로 광대역통합망 M-BcN의 유,무선 이중화 장비의 유지보수를 1년 동안 하면서 구축 된 시스템의 유지보수 보다도 창의적인 네트워크 구축 및 개발분야에 관심이 가서 지원하게 되었습니다. 네트워크업무를 하기 위한 저의 노력으로는 실무와 괴리감을 줄이기 위해 현 네트워크에서 일하는 친구의 도움으로 관련 전문용어들을 공부하며 지식을 쌓으며, 다가가고 있습니다. 회사 입사 후에도 자기계발을 꾸준하게 하며, 회사에 이바지 하도록 하겠습니다."
        	            },
        	            {
        	                "title": "장단점",
        	                "charNum": 282,
        	                "contents": "매사에 꼼꼼하고 확실히 일을 처리합니다. 저는 꼼꼼하고 섬세한 성격을 지니고 있습니다. 장점으로는 맡은업무에 신경을 많이 써 처리하기 때문에 작은 일 하나 하더라도 사고나 실수를 줄일 수 있으며, 세심하게 주의를 기울이기에 만족스러운 결과를 얻는 편입니다. 하지만 단점으로는 섬세하게 작업하다 보니 업무 처리 속도가 느려져 업무 효율이 떨어지는 것을 느꼈습니다. 이러한 단점을 보완하기 위해 신경 써서 해야 할 작업만 골라 꼼꼼하게 처리하며, 그렇지 않은 것에 대해선 빠르게 처리하도록 노력하고 있습니다."
        	            },
        	            {
        	                "title": "문제 해결 능력",
        	                "charNum": 414,
        	                "contents": "팀 프로젝트에서 의견 충돌이 발생했을 때, 저는 공정성의 관점을 가지고 의견을 조율하였습니다. OWASP TOP 10을 기반으로 한 버그바운티 취약점 분석 프로젝트에서, 팀장과 팀원들 사이의 프로젝트 방향성에 대한 의견이 일치하지 않았습니다.\n\n팀장은 하나의 취약점을 모두가 함께 학습하고 공유하는 방식을 제안하였고, 저와 나머지 팀원들은 각자 다른 취약점을 학습하고 공유하는 방식을 제안하였습니다. 의견 충돌을 해결하기 위해, 저는 모든 팀원들의 의견을 차례로 듣고 정리하였습니다.\n\n결국, 1주 동안 각자의 방식을 적용해 보고, 그 결과를 바탕으로 가장 효율적인 방식을 선택하기로 합의하였습니다. 이 경험을 통해, 의견 충돌이 발생할 때 소수 의견이라도 반영하고 적용해보는 것이 효과적인 해결책을 찾는데 도움이 된다는 것을 깨달았습니다."
        	            }
        	        ],
        	        "portfolios": [
        	            {
        	                "portfolioDiv": "이력서",
        	                "portfolioType": "파일",
        	                "portfolioUrl": "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/1f3ac674-20f5-429b-8005-c4feef6d2f5e_news-p.v1.20240912.bcf62a6ea3f74e968feb749ff9a88b4b.jpg"
        	            }
        	        ]
        		    }
        		}
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
        		{
        		    "code": "300",
        		    "isSuccess": false,
        		    "message": "지원서 조회에 실패했습니다.",
        			  "result": {
        			     {error.details} || null
        			  }
        		}
            ```
        ```
        
- **`[GET]` 공고 지원서 목록 조회 (지원자)**
    
    
    | **요구사항 ID** | **RESUME_012** |
    | --- | --- |
    | Description | 지원자의 지원서를 조회 |
    | URL | /api/resume/read-all |
    | Auth Required | Yes |
    
    | **`request params`** | **`요구 사항 상세`** |
    | --- | --- |
    | page,
    size | **[지원자의 자신이 지원한 공고용 지원서 조회]**
    1. 표시 정보
    - 공고이름
    - 기업 이름
    - 지원서 제목
    - 최종 상태
    - 지원날짜 |
    - **✅ Response 200**
        
        ```json
            ```json
            {
        	    "success": true,
        	    "code": 2100,
        	    "message": "지원서 조회에 성공하였습니다.",
        	    "result": {
        	        "content": [
        	            {
        	                "resumeIdx": 26,
        	                "resumeTitle": "지원자1",
        	                "resumedAt": "2024-10-22T17:28:01.735004",
        	                "announcementIdx": 507,
        	                "announcementTitle": "삼성증권 신입사원 채용 공고",
        	                "announcementStart": "2024-10-22 07:00:00",
        	                "announcementEnd": "2024-11-22 18:00:00",
        	                "companyName": "삼성 증권",
        	                "resumeResult": true,
        	                "interviewOneResult": true,
        	                "interviewTwoResult": true,
        	                "finalResult": true
        	            }
        	        ],
        	        "pageable": {
        	            "pageNumber": 0,
        	            "pageSize": 10,
        	            "sort": [],
        	            "offset": 0,
        	            "paged": true,
        	            "unpaged": false
        	        },
        	        "totalElements": 1,
        	        "totalPages": 1,
        	        "last": true,
        	        "size": 10,
        	        "number": 0,
        	        "sort": [],
        	        "numberOfElements": 1,
        	        "first": true,
        	        "empty": false
        		    }
        		}
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
        		{
        		    "code": "300",
        		    "isSuccess": false,
        		    "message": "지원서 조회에 실패했습니다.",
        			  "result": {
        			     {error.details} || null
        			  }
        		}
            ```
        ```
        
- **`[GET]` 공고 지원서 목록 조회 (채용담당자)**
    
    
    | **요구사항 ID** | **RESUME_011, RECRUITER_PAGE_006** |
    | --- | --- |
    | Description | 채용담당자는 공고별 지원자의 지원서들을 목록 조회한다. |
    | URL | /api/resume/recruiter/read-all |
    | Auth Required | Yes |
    
    | **`request params`** | **`요구 사항 상세`** |
    | --- | --- |
    | announcementIdx,
    page,
    size
     | **[공고에 지원한 지원서들을 조회]**
    1. 표시 정보
    - 이름
    - 지원서 제목
    - 지원 날짜
    - 결과
    2. 페이징 처리 |
    - **✅ Response 200**
        
        ```json
            ```json
            {
        	    "success": true,
        	    "code": 2300,
        	    "message": "지원자 조회에 성공하였습니다.",
        	    "result": {
        	        "content": [
        	            {
        	                "resumeIdx": 29,
        	                "seekerName": "지원자4",
        	                "resumeResult": true,
        	                "interviewOneResult": null,
        	                "interviewTwoResult": null,
        	                "finalResult": null,
        	                "resumeTitle": "지원자4의 지원서",
        	                "resumedAt": "2024-10-23T10:30:47.687102"
        	            },
        	            {
        	                "resumeIdx": 28,
        	                "seekerName": "지원자3",
        	                "resumeResult": true,
        	                "interviewOneResult": null,
        	                "interviewTwoResult": null,
        	                "finalResult": null,
        	                "resumeTitle": "지원자3의 지원서",
        	                "resumedAt": "2024-10-23T10:27:41.957475"
        	            },
        	            {
        	                "resumeIdx": 27,
        	                "seekerName": "지원자2",
        	                "resumeResult": true,
        	                "interviewOneResult": true,
        	                "interviewTwoResult": true,
        	                "finalResult": true,
        	                "resumeTitle": "지원자2",
        	                "resumedAt": "2024-10-22T17:30:07.003915"
        	            },
        	            {
        	                "resumeIdx": 26,
        	                "seekerName": "지원자1",
        	                "resumeResult": true,
        	                "interviewOneResult": true,
        	                "interviewTwoResult": true,
        	                "finalResult": true,
        	                "resumeTitle": "지원자1",
        	                "resumedAt": "2024-10-22T17:28:01.735004"
        	            }
        	        ],
        	        "pageable": {
        	            "pageNumber": 0,
        	            "pageSize": 10,
        	            "sort": [],
        	            "offset": 0,
        	            "paged": true,
        	            "unpaged": false
        	        },
        	        "totalElements": 4,
        	        "totalPages": 1,
        	        "last": true,
        	        "size": 10,
        	        "number": 0,
        	        "sort": [],
        	        "numberOfElements": 4,
        	        "first": true,
        	        "empty": false
        		    }
        		}
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
        		{
        		    "code": "300",
        		    "isSuccess": false,
        		    "message": "지원자 조회에 실패했습니다.",
        			  "result": {
        			     {error.details} || null
        			  }
        		}
            ```
        ```
        

---

# 📋 Interview Schedule

- **`[POST]` 면접 일정 등록 및 알림 (신입, 경력)**
    
    
    | **요구사항 ID** | **INTERVIEW_SCHEDULE_001, INTERVIEW_SCHEDULE_002** |
    | --- | --- |
    | Description | 채용 담당자는 공고에 지원한 서류 합격 지원자의 면접 일정을 등록 |
    | URL | /api/interview-schedule/create |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | {
       "seekerList": [1, 2],
       "estimatorList": ["[test1-test1@test.com](mailto:test1-test1@test.com)", "[test2-test2@test.com](mailto:test2-test2@test.com)"],
       "isOnline": true,
       "interviewDate": "2024.09.28",
       "interviewStart": "13:00",
       "interviewEnd": "14:00",
       "interviewNum": 1,
       "careerBase": "신입",
       "announcementIdx": 1,
       "teamIdx": 1
    } | **[채용 담당자는 공고에 지원한 서류 합격 지원자의 면접 일정을 등록]**
    1. 채용 담당자는 공고별 서류에 합격한 신입 지원자들을 순차적으로 면접 일정을 배치
    2. 면접 일정을 배치하면, 지원자에게 알림이 전송
    3. 전체 면접 시작 일과 종료일은 동일
    4. 시간을 다르게 설정, 중복 x
    5. 대면 면접인지 화상 면접인지 구분
    6. 아직 면접 일정 확정전 → 확정시 등록된 면접진을 선택, 이메일 전송
    7. 아직 면접 일정 확정전 → 확정시 등록된 지원자에게 이메일 전송
    
    **[채용 담당자는 경력 면접자가 선택한 화상 면접 일정을 내부 면접진과 조율을 통해 제안]**
    1. 채용 담당자는 경력 지원자가 선택한 면접 가능일을 확인
    2. 채용 담당자는 면접 가능일에 대해 조정 필요시 제안 요청
    3. 경력 지원자는 채용담당자가 보낸 제안을 수락하면 알림 발생
    4. 전체 면접 시작 일과 종료일은 동일
    5. 시간을 다르게 설정, 중복 x
    6. 대면 면접인지 화상 면접인지 구분
    7. 채용 담당자는 경력자가 수락시 등록된 면접진을 선택, 이메일 전송
    8. 채용 담당자는 지원자에게 면접일정 알림, 이메일 전송 |
    - **✅ Response 200**
        
        ```json
        ```json
        {
        	"code": "200",
        	"isSuccess": true,
        	"message": "면접 일정 등록에 성공했습니다.",
        	"result": {
        		  "seekerList": [1, 2],
        		  "estimatorList": ["[test1-test1@test.com](mailto:test1-test1@test.com)", "[test2-test2@test.com](mailto:test2-test2@test.com)"],
        		  "isOnline": true,
        		  "interviewDate": "2024.09.28",
        		  "interviewStart": "13:00",
        	    "interviewEnd": "14:00",
        	    "interviewNum": 1,
        	    "careerBase": "신입",
        	    "announcementIdx": 1,
        	    "teamIdx": 1
        	    "companyName": "신한은행",
        	    "announcementTitle" : "백엔드 개발 대규모 신입 채용",
        	    "countInterviewSchedule": 0
        	}
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": "300",
        	"isSuccess": false,
        	"message": "면접 일정 등록에 실패했습니다.",
          "result": {
             {error.details} || null
          }
        }
        ```
        ```
        
- **`[GET]` 면접 일정 목록 조회 (신입, 경력)**
    
    
    | **요구사항 ID** | **INTERVIEW_SCHEDULE_006** |
    | --- | --- |
    | Description | 채용 담당자는 지원자들의 면접 일정을 목록 조회 가능 |
    | URL | /api/interview-schedule/read-all |
    | Auth Required | Yes |
    
    | **`request params`**  | **`요구 사항 상세`**  |
    | --- | --- |
    | careerBase, announcementIdx, interviewNum, pageNum | **[채용담당자는 지원자들의 면접 일정 목록 조회 가능]**
    1. 채용 담당자는 아직 면접 일정이 등록된 서류 합격 신입 지원자를 목록 조회
    2. 채용 담당자는 아직 면접 일정이 등록된 경력 지원자를 목록 조회
    3.  클라이언트에서 1, 2, 3을 구분하는 건 단순 텍스트나, 항목의 배경을 조절
    4. 클라이언트에서 페이지 네이션으로 목록을 조회 가능
    5. 채용담당자는 로그인 정보와 신입/경력 구분을 통해 등록된 각각의 공고 목록을 조회 ⇒ 공고기간, 공고명
    6. 채용담당자는 공고 선택(공고idx)을 통해 해당 공고에서 현재 등록된 면접 목록을 조회 ⇒ 면접일. 시간, 팀, 면접자 목록 |
    - **✅ Response 200**
        
        ```json
        ```json
        {
            "success": true,
            "code": 200,
            "message": "면접 내역 조회에 성공했습니다.",
            "result": {
                "content": [
                    {
                        "idx": 41,
                        "seekerList": [
                            {
                                "idx": 5336,
                                "name": "서시현",
                                "email": "sihyun_s@kakao.com"
                            },
                            {
                                "idx": 5338,
                                "name": "구은주",
                                "email": "gueunju10@gmail.com"
                            }
                        ],
                        "estimatorList": [
                            {
                                "idx": 20,
                                "email": "adordopamines@gmail.com",
                                "password": null,
                                "name": "면접자",
                                "role": null,
                                "emailAuth": null
                            },
                            {
                                "idx": 20,
                                "email": "adordopamines@gmail.com",
                                "password": null,
                                "name": "면접자",
                                "role": null,
                                "emailAuth": null
                            }
                        ],
                        "isOnline": true,
                        "interviewDate": "2024-10-28",
                        "interviewStart": "09:00",
                        "interviewEnd": "18:00",
                        "interviewNum": null,
                        "uuid": "0e872ffb-9404-4fb6-a60d-bc08faa9e755",
                        "careerBase": "신입",
                        "teamIdx": 1,
                        "companyName": "넷마블",
                        "announcementTitle": "선도기업 프론트엔드 채용 공고",
                        "countInterviewSchedule": 1
                    }
                ],
                "pageable": {
                    "pageNumber": 0,
                    "pageSize": 10,
                    "sort": [
                        {
                            "direction": "DESC",
                            "property": "idx",
                            "ignoreCase": false,
                            "nullHandling": "NATIVE",
                            "ascending": false,
                            "descending": true
                        }
                    ],
                    "offset": 0,
                    "paged": true,
                    "unpaged": false
                },
                "totalElements": 1,
                "totalPages": 1,
                "last": true,
                "size": 10,
                "number": 0,
                "sort": [
                    {
                        "direction": "DESC",
                        "property": "idx",
                        "ignoreCase": false,
                        "nullHandling": "NATIVE",
                        "ascending": false,
                        "descending": true
                    }
                ],
                "numberOfElements": 1,
                "first": true,
                "empty": false
            }
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": "300",
        	"isSuccess": false,
        	"message": "면접 내역 조회에 실패했습니다.",
          "result": {
             {error.details} || null
          }
        }
        ```
        ```
        
- **`[GET]` 면접 일정 상세 조회 (신입, 경력)**
    
    
    | **요구사항 ID** | **INTERVIEW_SCHEDULE_005, INTERVIEW_SCHEDULE_004** |
    | --- | --- |
    | Description | 채용 담당자는 경력 지원자들의 면접 일정을 상세 조회 가능 |
    | URL | /interview-schedule/read |
    | Auth Required | Yes |
    
    | **`request params`**  | **`요구 사항 상세`**  |
    | --- | --- |
    | interviewScheduleIdx | [채용 담당자는 경력 지원자들의 면접 일정을 상세 조회 가능]
    1. 경력 지원자들의 일정은 자신이 정함
    2. 채용 담당자는 경력 지원자들의 면접 일정을 목록 조회 가능
    3. 경력 지원자들의 면접 일정 목록에서 단일 항목 선택 및 모달창 오픈
    4. 부득이한 사유로 인해 일정이 수정될 경우 모달창에서 정해진 일정을 수정 가능
    5. 마이페이지에서 경력 지원자는 면접 일정을 확인 가능 |
    - **✅ Response 200**
        
        ```json
        ```json
        {
            "success": true,
            "code": 8001,
            "message": "면접 내역 조회에 성공했습니다.",
            "result": {
                "idx": 41,
                "seekerList": [
                    {
                        "idx": 5336,
                        "name": "서시현",
                        "email": "sihyun_s@kakao.com"
                    },
                    {
                        "idx": 5338,
                        "name": "구은주",
                        "email": "gueunju10@gmail.com"
                    }
                ],
                "estimatorList": [
                    {
                        "idx": 20,
                        "email": "adordopamines@gmail.com",
                        "password": null,
                        "name": "면접자",
                        "role": null,
                        "emailAuth": null
                    }
                ],
                "isOnline": true,
                "interviewDate": "2024-10-28",
                "interviewStart": "09:00",
                "interviewEnd": "18:00",
                "interviewNum": 1,
                "uuid": "0e872ffb-9404-4fb6-a60d-bc08faa9e755",
                "careerBase": null,
                "teamIdx": 1,
                "companyName": null,
                "announcementTitle": null,
                "countInterviewSchedule": null
            }
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": "300",
        	"isSuccess": false,
        	"message": "면접 내역 조회에 실패했습니다.",
        	"result": {
        		error.details
        	}
        }
        ```
        ```
        
- **`[POST]` 채용 담당자 경력 지원자 면접 일정 조율**
    
    
    | **요구사항 ID** | **INTERVIEW_SCHEDULE_007** |
    | --- | --- |
    | Description | 채용 담당자는 경력 면접자가 선택한 화상 면접 일정을 내부 면접진과 조율을 통해 제안 |
    | URL | /interview-schedule/update |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | {
    ”interviewScheduleIdx”: 1
    ”seekerList”: [1, 2],
    ”reScheduleIdx”: 2
    } | **[채용담당자는 지원자들의 면접 일정을 수정 가능]**
    1. 채용 담당자는 경력지원자의 경우 일정 제안을 수락시 조정하고 알림
    2. 채용담당자는 기존 면접 일정 정보를 interviewScheduleIdx를 통해 조회하고, 수정 후 알림
    3. 수정 정보
    - 면접 참여자 정보 status |
    - **✅ Response 200**
        
        ```json
        ```json
        {
        	"code": 8002,
        	"isSuccess": true,
        	"message": "면접 내역 업데이트에 성공했습니다.",
        	"result": { 
        	}
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": 8009,
        	"isSuccess": false,
        	"message": "면접 연관 정보를 찾을 수 없습니다.",
          "result": {
             {error.details} || null
          }
        }
        ```
        ```
        
- **`[POST]` 면접 일정 조율 생성(지원자)**
    
    
    | **요구사항 ID** | **INTERVIEW_SCHEDULE_008** |
    | --- | --- |
    | Description | 경력 지원자는 면접 요청 알림을 받고, 모달창을 통해 면접 가능한 일정을 선택 |
    | URL | /interview-schedule/create/re-schedule |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | {
    ”interviewScheduleIdx”: 1
    ”interviewStart”: “2024-10-28”,
    ”interviewEnd”: “2024-11-10”
    } | **[경력 지원자들은 면접 일정 조율을 날짜 범위 입력을 통해 요청 가능]**
    1. 경력 지원자들은 모달창을 통해 조율을 원하는 날짜 범위를 입력
    2. 생성 정보
    - interviewStart
    - interviewEnd
    - interviewScheduleIdx
    - seekerIdx
    - status |
    - **✅ Response 200**
        
        ```json
        ```json
        {
        	"code": 8005,
        	"isSuccess": true,
        	"message": "면접 조율 신청 생성 완료",
        	"result": {
        		"idx" : 1,
        		"interviewStart" : "2024-10-28",
        		"interviewEnd" : "2024-11-10",
        		"interviewScheduleRes" : {
        			"idx" : 2,
        			"estimatorList" : [
        				{ "email" : "estimator1@test.com", "name" : "면접관1" },
        				{ "email" : "estimator2@test.com", "name" : "면접관2" }
        			],
        			"isOnline" : true,
        			"interviewDate" : "2024-11-30",
        			"interviewStart" : "09:00",
        			"interviewEnd" : "12:00",
        			"interviewNum" : 2,
        			"uuid" : "284d15ed-0f54-47db-b3ff-62b66f45708b",
        			"careerBase" : "경력",
        			"teamIdx" : 1,
        			"companyName" : "한화시스템",
        			"announcementTitle" : "백엔드 개발자 경력 채용",
        			"countInterviewSchedule" : 4
        		}
        		"seekerInfoGetRes" : 
        		"status" : false
        	}
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": 8003,
        	"isSuccess": false,
        	"message": "해당 면접을 찾을 수 없습니다.",
          "result": {
             {error.details} || null
          }
        }
        ```
        ```
        
- **`[GET]` 면접 일정 조율 조회(채용담당자)**
    
    
    | **요구사항 ID** | **INTERVIEW_SCHEDULE_009** |
    | --- | --- |
    | Description | 채용담당자는 공고별 면접 일정 조율 목록을 조회한다. |
    | URL | /interview-schedule/read-all/re-schedule |
    | Auth Required | Yes |
    
    |  **`request params`** | **`요구 사항 상세`** |
    | --- | --- |
    | announcementIdx
    pageNum | **[면접 일정 조율 목록 조회]**
    1. 채용담당자는 공고별 면접 일정 조율 목록을 조회한다.
    2. ReSchedule 테이블의 announcementIdx를 이용해 저장된 전체 면접 일정 조율 목록 리스트를 가져옴 |
    - **✅ Response 200**
        
        ```json
        ```json
        {
        		"success": true,
            "code": 8007,
            "message": "면접 조율 내역 조회에 성공했습니다.",
            "result": [
                {
                    "idx": 2,
                    "interviewStart": "2024-10-21",
                    "interviewEnd": "2024-10-21",
                    "interviewScheduleRes": {
                        "idx": 12,
                        "seekerList": null,
                        "estimatorList": null,
                        "isOnline": true,
                        "interviewDate": "2024-10-19",
                        "interviewStart": "15:30",
                        "interviewEnd": "17:00",
                        "interviewNum": 2,
                        "uuid": "19bead32-f178-4a26-a0cc-4964a35e3d30",
                        "careerBase": null,
                        "teamIdx": 1,
                        "companyName": null,
                        "announcementTitle": null,
                        "countInterviewSchedule": null
                    },
                    "seekerInfoGetRes": {
                        "idx": 7,
                        "name": "지원자2",
                        "email": "seeker2@test.com"
                    },
                    "status": false
                },
                {
                    "idx": 3,
                    "interviewStart": "2024-10-20",
                    "interviewEnd": "2024-10-20",
                    "interviewScheduleRes": {
                        "idx": 12,
                        "seekerList": null,
                        "estimatorList": null,
                        "isOnline": true,
                        "interviewDate": "2024-10-19",
                        "interviewStart": "15:30",
                        "interviewEnd": "17:00",
                        "interviewNum": 2,
                        "uuid": "19bead32-f178-4a26-a0cc-4964a35e3d30",
                        "careerBase": null,
                        "teamIdx": 1,
                        "companyName": null,
                        "announcementTitle": null,
                        "countInterviewSchedule": null
                    },
                    "seekerInfoGetRes": {
                        "idx": 7,
                        "name": "지원자2",
                        "email": "seeker2@test.com"
                    },
                    "status": false
                },
            ]
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": 8008,
        	"isSuccess": false,
        	"message": "면접 일정 조율 목록 조회에 실패했습니다.",
          "result": {
             {error.details} || null
          }
        }
        ```
        ```
        

---

# 😎 Video Interview

- **`[POST]` 화상 면접 방 생성 (채용담당자)**
    
    
    | **요구사항 ID** | **VIDEO_INTERVIEW_001** |
    | --- | --- |
    | Description | 채용 담당자는 화상 면접방을 생성 |
    | URL | /api/video-interview/create |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | {
       "announceUUID": "d3206542-c5a9-4184-   a42a-06a4bd1bfa1a",
       "videoInterviewUUID":"abcdefg",
       "params": { "customSessionId": "abcdefg"}
    } | **[채용 담당자는 화상 면접방을 생성]**
    1. kurento, webrtc 중계서버를 두는 SFU 방식으로 구현
    2. OpenVidu 라이브러리 사용
    3. 면접 일정생성 후 방 생성 버튼을 누르면 OpenVidu의 세션이 생성 |
    - **✅ Response 200**
        
        ```json
        ```json
        {
            "success": true,
            "code": 200,
            "message": "화상 면접 방 생성에 성공했습니다.",
            "result": {
                "idx": 33,
                "interviewScheduleRes": {
                    "idx": 42,
                    "seekerList": [
                        {
                            "idx": 328,
                            "name": "지원자1",
                            "email": "okqkrwhdtjd@naver.com"
                        }
                    ],
                    "estimatorList": [
                        {
                            "idx": 21,
                            "email": "dddddddddd@test.com",
                            "password": null,
                            "name": "test",
                            "role": null,
                            "emailAuth": null
                        }
                    ],
                    "isOnline": true,
                    "interviewDate": "2024-10-28",
                    "interviewStart": "00:00",
                    "interviewEnd": "05:00",
                    "interviewNum": null,
                    "uuid": "0e872ffb-9404-4fb6-a60d-bc08faa9e755",
                    "careerBase": "신입",
                    "teamIdx": 1,
                    "companyName": "삼성 증권",
                    "announcementTitle": "삼성화재",
                    "countInterviewSchedule": 1
                },
                "announcementUuid": "a18c1817-0687-400f-b0e7-8591a88c0679"
            }
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": "300",
        	"isSuccess": false,
        	"message": "화상 면접방 생성에 실패했습니다.",
          "result": {
             {error.details} || null
          }
        }
        ```
        ```
        
- **`[GET]` 화상 면접 방 목록 조회 (면접관, 지원자)**
    
    
    | **요구사항 ID** | **VIDEO_INTERVIEW_007** |
    | --- | --- |
    | Description | 지원자와 면접관은 화상 면접 대기방에 입장시 화상 면접 방 목록을 조회한다. |
    | URL | /api/video-interview/read-all |
    | Auth Required | Yes |
    
    | **`request params`** | **`요구 사항 상세`** |
    | --- | --- |
    | announcementUUID | **[화상 면접 방 목록 조회]**
    1. 지원자와 면접관은 화상 면접 대기방에 입장시 화상 면접 방 목록을 조회한다.
    2. 면접 대기방에 입장시 해당 공고의 자신이 속한 면접 일정 목록을 조회
    3. VideoInterViewRoom 테이블의 공고 UUID를 이용해 저장된 값을 전체 조회해서 화상 면접방 리스트를 가져옴 |
    - **✅ Response 200**
        
        ```json
        ```json
        {
            "success": true,
            "code": 8101,
            "message": "화상 면접방 목록 조회에 성공했습니다.",
            "result": [
                {
                    "announcementTitle": "삼성화재",
                    "announcementUUID": "a18c1817-0687-400f-b0e7-8591a88c0679",
                    "interviewScheduleUUID": "0e872ffb-9404-4fb6-a60d-bc08faa9e755",
                    "interviewDate": "2024-10-28",
                    "interviewStart": "00:00",
                    "interviewEnd": "05:00"
                }
            ]
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": "300",
        	"isSuccess": false,
        	"message": "화상 면접방 목록 조회에 실패했습니다.",
          "result": {
             {error.details} || null
          }
        }
        ```
        ```
        
- **`[POST]` 화상 면접 방 참여 (면접관, 지원자)**
    
    
    | **요구사항 ID** | **VIDEO_INTERVIEW_005** |
    | --- | --- |
    | Description | 생성된 화상 면접방에 정해진 면접진과 지원자가 참여한다. |
    | URL | /api/video-interview/get-session-token |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | {
       "announceUUID": "49dc571f-6218-47ab-9a50-e660fa5e7134",
       "videoInterviewUUID":"9f974540-9e84-4096-9e71-193526de793a",
       "params": { "customSessionId": "9f974540-9e84-4096-9e71-193526de793a"}
    } | **[생성된 화상 면접방에 정해진 면접진과 지원자가 참여]**
    1. kurento, webrtc 중계서버를 두는 SFU 방식으로 구현 OpenVidu 사용
    2. 전송된 화상 면접 링크를 통해 지원자와 면접진은 화상 면접방에 접속한다.
    3. 시간대 접근제어 ⇒ 면접 시간이 아닌데 면접방에 접속하려는 사용자 접근 막음
    4. 공고별 접근제어 ⇒ 공고에 지원하지 않은 사용자 접근 막음 |
    - **✅ Response 200**
        
        ```json
        ```json
        {
            "success": true,
            "code": 8103,
            "message": "화상 면접방에 참가했습니다.",
            "result": {
                "sessionToken": "wss://www.irs-openviddd.kro.kr?sessionId=0e872ffb-9404-4fb6-a60d-bc08faa9e755&token=tok_Y4UnYTk64PZncI1S",
                "userEmail": "okqkrwhdtjd@naver.com",
                "userType": "ROLE_SEEKER"
            }
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": "300",
        	"isSuccess": false,
        	"message": "화상 면접방에 참가 실패했습니다.",
          "result": {
             {error.details} || null
          }
        }
        ```
        ```
        

---

# 📋 Interview Evaluate

- **`[POST]` 화상 면접 평가표 등록 (채용담당자)**
    
    
    | 요구사항 ID | **INTERVIEW_EVALUATE_001** |
    | --- | --- |
    | Description | 채용담당자는 화상 면접방에서 면접자가 평가할 평가표를 등록한다. |
    | URL | /api/interview-evaluate/create-form |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | {
       "announceIdx": 511,
       "q1": "평가항목1",
       "q2": "평가항목2",
       "q3": "평가항목3",
       "q4": "평가항목4",
       "q5": "평가항목5",
       "q6": "평가항목6",
       "q7": "평가항목7",
       "q8": "평가항목8",
       "q9": "평가항목9",
       "q10": "평가항목10"
    } | [화상 면접방에서 면접자가 평가할 평가표를 등록]
    1. 면접 평가항목1~10을 등록
    2. InterviewEvaluateController create-form으로 요청을 보내고 해당 공고의 채용담당자가 맞으면 등록
    3. 만약 한개의 면접 평가표가 등록되어있으면 다시 수정 |
    - **✅ Response 200**
        
        ```json
        ```json
        {
        	"code": "200",
        	"isSuccess": true,
        	"message": "인터뷰 평가표 생성에 성공했습니다.",
        	"result": { 
        			"idx" : 1
        	}
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": "300",
        	"isSuccess": false,
        	"message": "인터뷰 평가표 생성에 실패했습니다.",
          "result": {
             {error.details} || null
          }
        }
        ```
        ```
        
- **`[GET]` 화상 면접 평가표 조회 (면접관)**
    
    
    | **요구사항 ID** | **INTERVIEW_EVALUATE_003** |
    | --- | --- |
    | Description | 면접시 면접관은 채용담당자가 등록한 화상 면접 평가표를 조회한다. |
    | URL | /api/interview-evaluate/search-form |
    | Auth Required | Yes |
    
    | **`request params`** | **`요구 사항 상세`** |
    | --- | --- |
    | announcementUUID, interviewScheduleUUID | **[면접관 지원자 평가표 조회]
    1. 면접시 면접관은 지원자를 평가하기 위해 채용담당자가 등록한 화상 면접 평가표를 조회한다.
    2**.  InterviewEvaluate, InterviewEvaluateForm, InterviewEvaluateResult 엔티티, 연관관계 매핑생성
    3. Announcement, InterviewEvaluteForm announceUUID로 연관관계 매핑
    4. announcementUUID로 InterviewEvaluteForm을 가져옴 |
    - **✅ Response 200**
        
        ```json
        ```json
        {
            "success": true,
            "code": 200,
            "message": "인터뷰 평가표 조회에 성공했습니다.",
            "result": {
                "q1": "1",
                "q2": "2",
                "q3": "3",
                "q4": "4",
                "q5": "5",
                "q6": "6",
                "q7": "7",
                "q8": "8",
                "q9": "9",
                "q10": "10"
            }
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": "300",
        	"isSuccess": false,
        	"message": "인터뷰 평가표 조회에 실패했습니다.",
          "result": {
             {error.details} || null
          }
        }
        ```
        ```
        
- **`[GET]` 화상 면접 평가를 위한 지원서 조회 (면접관)**
    
    
    | **요구사항 ID** | **INTERVIEW_EVALUATE_002** |
    | --- | --- |
    | Description | 면접관의 지원자 지원서 조회 기능을 개발한다. |
    | URL | /api/interview-evaluate/read-all/resume-info |
    | Auth Required | Yes |
    
    | **`request params`** | **`요구 사항 상세`** |
    | --- | --- |
    | announcementUUID, interviewScheduleUUID | **[면접관의 지원자 지원서 조회 기능을 개발]**
    1. Controller 생성(api/interivew-evaluate/read-all/resume-info)
    2. InterviewEvaluateReadAllResumeInfo DTO 생성 (Map형식, key=지원자idx, value=지원자의 지원서)
    3. InterviewEvaluateReadResumeInfoRes DTO 생성 (지원자의 지원서)
    4. InterviewEvaluateService 생성
    - InterviewParticipate를 면접관 idx와 면접일정 UUID로 조회 => 면접일정에 속한 지원자들
    - 반복문을 수행해 지원자들의 지원서를 InterviewEvaluateReadResumeInfoRes에 담음
    - key=지원자idx, value=지원자의 지원서 Map 형식으로 반환 |
    - **✅ Response 200**
        
        ```json
        ```json
        {
            "success": true,
            "code": 200,
            "message": "지원자들의 지원서 정보를 불러오는데 성공했습니다.",
            "result": {
                "interviewEvaluateReadResumeInfoResMap": {
                    "328": {
                        "personalInfo": {
                            "name": "지원자1",
                            "birth": "2000.10.01",
                            "gender": "남자",
                            "email": "okqkrwhdtjd@naver.com",
                            "phone": "010-1234-1234",
                            "tel": null,
                            "address": "서울 동대문구 서울시립대로 5 ",
                            "profileImg": "https://pjs2024-s3.s3.ap-northeast-2.amazonaws.com/521b7731-3d6a-43d9-adbc-2234b75d7c0e_openvidu_globe_bg_transp_cropped.png"
                        },
                        "preferentialEmp": {
                            "veterans": false,
                            "protection": false,
                            "subsidy": false,
                            "disability": false,
                            "disabilityDegree": "",
                            "military": true,
                            "militaryClass": "군필",
                            "militaryStart": null,
                            "militaryEnd": null,
                            "militaryType": "육군",
                            "militaryRank": "병장"
                        },
                        "educations": [
                            {
                                "schoolDiv": "고등학교",
                                "schoolName": "고등어학교",
                                "enteredAt": null,
                                "graduatedAt": "2016.02",
                                "graduationStatus": "졸업",
                                "majorName": null,
                                "grade": null,
                                "totalGrade": null,
                                "transfer": false,
                                "majorType": "",
                                "otherMajor": null,
                                "graduationWork": null,
                                "degree": "",
                                "qualificationExam": false,
                                "passedAt": null
                            }
                        ],
                        "personalHistories": [
                            {
                                "companyName": "경력회사",
                                "deptName": "it",
                                "enteredAt": "2023.03",
                                "quitAt": "2023.03",
                                "empStatus": false,
                                "position": "사원",
                                "job": "개발",
                                "salary": 10000,
                                "work": "열심히 다녔습니다."
                            }
                        ],
                        "internsActivities": [
                            {
                                "activityDiv": "인턴",
                                "organization": "삼성 증권 ",
                                "startAt": "2023.03",
                                "endAt": "2023.03",
                                "contents": "열심히 다녔습니다."
                            }
                        ],
                        "studyingAbroads": null,
                        "languages": null,
                        "certifications": null,
                        "trainings": [
                            {
                                "trainingName": "한화시스템 Beyond SW 캠프",
                                "organization": "삼성 증권 ",
                                "startAt": "2023.03",
                                "endAt": "2023.03",
                                "contents": "열심히 다녔습니다."
                            }
                        ],
                        "awards": null,
                        "customLetters": [
                            {
                                "title": "a",
                                "charNum": 1,
                                "contents": "a"
                            },
                            {
                                "title": "b",
                                "charNum": 1,
                                "contents": "a"
                            },
                            {
                                "title": "c",
                                "charNum": 1,
                                "contents": "a"
                            }
                        ],
                        "portfolios": null
                    }
                }
            }
        }
        ```
        ```
        
    - ❌ **error Response**
        
        ```json
        ```json
        {
        	"code": "300",
        	"isSuccess": false,
        	"message": "지원자들의 지원서 정보를 불러오는데 실패했습니다",
          "result": {
             {error.details} || null
          }
        }
        ```
        ```
        
- **`[POST]` 화상 면접 평가 제출 (면접관)**
    
    
    | 요구사항 ID | **INTERVIEW_EVALUATE_004** |
    | --- | --- |
    | Description | 면접관은 지원자의 화상 면접 평가를 제출한다.. |
    | URL | /api/interview-evaluate/create-evaluate |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | {
       "userEmail": "[okqkrwhdtjd@naver.com](mailto:okqkrwhdtjd@naver.com)",
       "scores": {
          "1": 1,
          "2": 1,
          "3": 1,
          "4": 1,
          "5": 1,
          "6": 1,
          "7": 1,
          "8": 1,
          "9": 1,
          "10": 1
       },
       "totalScore": 10,
       "comments": "불합격",
       "announcementUUID": "a18c1817-0687-400f-b0e7-8591a88c0679",
       "videoInterviewUUID": "0e872ffb-9404-4fb6-a60d-bc08faa9e755"
    } |  **[면접관 화상 면접 평가 제출]**
     면접 참여자 찾고, 인터뷰 평가 폼을 가져오고, 없으면 예외
    면접 참여자에 대한 기존 평가가 있는지 확인
     기존 면접 평가가 있을 경우, 업데이트
      |
    - **✅ Response 200**
        
        ```json
        ```json
        {
            "success": true,
            "code": 200,
            "message": "해당 지원자의 인터뷰 평가를 생성하는데 성공했습니다.",
            "result": {
                "idx": 23
            }
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": "300",
        	"isSuccess": false,
        	"message": "해당 지원자의 인터뷰 평가를 생성하는데 실패했습니다.",
          "result": {
             {error.details} || null
          }
        }
        ```
        ```
        
- **`[GET]` 화상 면접 평가 결과 목록 조회 (채용담당자)**
    
    
    | 요구사항 ID | INTERVIEW_EVALUATE_004 |
    | --- | --- |
    | Description | 채팅방에 지원자가 참여한다. |
    | URL | /api/interview-evaluate/read-all/evaluate |
    | Auth Required | Yes |
    
    | **`request params`** | **`요구 사항 상세`** |
    | --- | --- |
    | interviewNum,
    announceIdx | **[채용 담당자는 화상 면접이 끝나고 지원자들의 평가를 목록 조회]
    1.** 지원자의 면접이 끝나고 면접진이 해당 지원자의 면접 평가 표를 제출
    2. 채용담당자는 지원자들에 대한 면접 평가 표를 조회 가능
    3. 클라이언트에서 지원자별 면접 평가표를 목록 조회 가능
    4. 면접진의 평가 결과표를 조회할 수 있고, 총 평가 점수를 알 수 있음 |
    - **✅ Response 200**
        
        ```json
        ```json
        {
            "success": true,
            "code": 6400,
            "message": "지원자들의 면접 평가 결과를 불러왔습니다.",
            "result": {
                "interviewEvaluateReadAllResMap": {
                    "328": [
                        {
                            "seekerName": "지원자1",
                            "seekerEmail": "okqkrwhdtjd@naver.com",
                            "totalScore": 10,
                            "comments": "불합격",
                            "estimatorName": "test",
                            "estimatorEmail": "dddddddddd@test.com",
                            "interviewEvaluateFormReadRes": {
                                "q1": "1",
                                "q2": "2",
                                "q3": "3",
                                "q4": "4",
                                "q5": "5",
                                "q6": "6",
                                "q7": "7",
                                "q8": "8",
                                "q9": "9",
                                "q10": "10"
                            },
                            "interviewEvaluateResultReadRes": {
                                "r1": 1,
                                "r2": 1,
                                "r3": 1,
                                "r4": 1,
                                "r5": 1,
                                "r6": 1,
                                "r7": 1,
                                "r8": 1,
                                "r9": 1,
                                "r10": 1
                            }
                        }
                    ]
                }
            }
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": "300",
        	"isSuccess": false,
        	"message": "지원자들의 면접 평가 결과를 불러오는데 실패했습니다.",
        	"result": {
        		error.details
        	}
        }
        ```
        ```
        

---

# 🏅 Total Process

- **`[POST]` 각 프로세스 결과 등록**
    
    
    | **요구사항 ID** | **TOTAL_PROCESS_001** |
    | --- | --- |
    | Description | 지원자들의 서류를 보고 합격, 불합격 여부 판단 |
    | URL | /api/total-process/create |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`**  |
    | --- | --- |
    | {
       “announcementIdx;” : 1
       ”seekerIdx;” : 1
       ”interviewNum” :  1    
       “isPass” : 0
    } | **[지원서 서류 합불]**
    1. 지원자들의 서류를 보고 합격, 불합격 여부 판단
    2. interviewNum 0 & isPass ⇒ 서류합격
    
    **[1차 면접 합불]**
    1. 면접관들이 평가한 항목을 조회후  합격, 불합격 여부 판단
    2. interviewNum 1 & isPass ⇒ 1차 면접 합격
    
    **[2차 면접 합불]**
    1. 면접관들이 평가한 항목을 조회후  합격, 불합격 여부 판단
    2. interviewNum 2 & isPass ⇒ 2차 면접 합격
    
    **[최종 결과]**
    1. 공고에서 1차까지 면접을 보면 1차합격시 최종 합격 처리
    2. 공고에서 2차까지 면접을 보면 2차합격시 최종 합격 처리 |
    - **✅ Response 200**
        
        ```json
        ```json
        {
        	"code": "200",
        	"isSuccess": true,
        	"message": "통합 지원 프로세스 생성에 성공했습니다.",
        	"result": {
        			"idx" : 1
        	}
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": "300",
        	"isSuccess": false,
        	"message": "통합 지원 프로세스 생성에 실패했습니다.",
        	"result": {
        		error.details
        	}
        }
        ```
        ```
        
- **`[GET]` 채용담당자  지원자  채용 결과 조회**
    
    
    | **요구사항 ID** | **TOTAL_PROCESS_002** |
    | --- | --- |
    | Description | 채용 담당자는 공고별 지원자의 지원서들을 목록 조회 |
    | URL | /resume_status/read-all?announce-idx={annouce_idx} |
    | Auth Required | Yes |
    
    | **`request params`** | **`요구 사항 상세`**  |
    | --- | --- |
    | announcementIdx, 
    page, 
    size | **[채용담당자는 공고에 지원한 지원자의 결과들을 조회]**
    1. 공고에 지원한 지원자 결과 내역 페이징 처리
    2. 서류 합격 여부 조회
    3. 1차 합격 여부 조회
    4. 2차 합격 여부 조회
    5. 최종 합격 여부 조회 |
    - **✅ Response 200**
        
        ```json
        ```json
        {
            "success": true,
            "code": 200,
            "message": "통합 지원 프로세스 조회에 성공했습니다.",
            "result": {
                "content": [
                    {
                        "totalProcessIdx": 25,
                        "resumeResult": true,
                        "interviewOneResult": null,
                        "interviewTwoResult": null,
                        "finalResult": null,
                        "seekerName": "지원자3"
                    },
                    {
                        "totalProcessIdx": 24,
                        "resumeResult": true,
                        "interviewOneResult": null,
                        "interviewTwoResult": null,
                        "finalResult": null,
                        "seekerName": "지원자4"
                    },
                    {
                        "totalProcessIdx": 23,
                        "resumeResult": true,
                        "interviewOneResult": true,
                        "interviewTwoResult": true,
                        "finalResult": true,
                        "seekerName": "지원자1"
                    },
                    {
                        "totalProcessIdx": 22,
                        "resumeResult": true,
                        "interviewOneResult": true,
                        "interviewTwoResult": true,
                        "finalResult": true,
                        "seekerName": "지원자2"
                    }
                ],
                "pageable": {
                    "pageNumber": 0,
                    "pageSize": 10,
                    "sort": [],
                    "offset": 0,
                    "paged": true,
                    "unpaged": false
                },
                "totalElements": 4,
                "totalPages": 1,
                "last": true,
                "size": 10,
                "number": 0,
                "sort": [],
                "numberOfElements": 4,
                "first": true,
                "empty": false
            }
        }
        ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
        ```json
        {
        	"code": "300",
        	"isSuccess": false,
        	"message": "통합 지원 프로세스 조회에 실패했습니다.",
        	"result": {
        		error.details
        	}
        }
        ```
        ```
        

---

# ⏰ Alarm

- **`[POST]` 알림 등록**
    
    
    | 요구사항 ID | **ALAM_001, ALAM_002** |
    | --- | --- |
    | Description | 알림을 등록한다. |
    | URL | /api/alarm/create |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    | {
       “type”: “알림 타입”,
       “status” : 알림 조회 여부,
       “message”: “알림 메시지”,
       “url”: “알림 url”,
       “createdAt” : “등록 날짜”,
       ”interviewScheduleIdx” : 1
    }
     | **[알림 등록]**
    1. 알림을 등록한다.
    2. 채용담당자와의 면접 일정 조율
    3. 지원한 서류의 합격 여부 알림
    4. 확정된 면접 일정(1차, 2차)에 대한 알림
    5. 최종 결과에 대한 알림 |
    - **✅ Response 200**
        
        ```json
            ```json
            {
                "code": "200",
                "isSuccess": true,
                "message": "알람 생성이 완료되었습니다.",
                "result": {
        				   "type" : "알림 타입",
        				   "status" : 알림 조회 여부,
        				   "message" : "알림 메시지",
        				   "url" : "알림 url",
        				   "createdAt" : "등록 날짜",
        				   "interviewScheduleIdx" : 1
            }
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
            {
                "code": "300",
                "isSuccess": false,
                "message": "알람 생성에 실패했습니다.",
                "result": {
        	         {error.details} || null
                }
            }
            ```
        ```
        
- **`[GET]` 알림 상태 수정**
    
    
    | 요구사항 ID | **ALAM_001, ALAM_002** |
    | --- | --- |
    | Description | 알림을 조회하면 상태값을 변경한다. |
    | URL | /api/alarm/update-status/{idx} |
    | Auth Required | No |
    
    | **`request params`** | **`요구 사항 상세`** |
    | --- | --- |
    | idx | **[알림 조회시 상태값 변경]**
    1. 알림을 조회하면 상태값을 변경한다. |
    - **✅ Response 200**
        
        ```json
            ```json
            {
                "code": "200",
                "isSuccess": true,
                "message": "알람 전체 조회에 성공했습니다.",
                "result": {
        		        [
        						   "type" : "알림 타입",
        						   "status" : 알림 조회 여부,
        						   "message" : "알림 메시지",
        						   "url" : "알림 url",
        						   "createdAt" : "등록 날짜",
        						   "interviewScheduleIdx" : 1
        		        ],
        
            }
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
            {
                "code": "300",
                "isSuccess": false,
                "message": "알람 상태 변경에 실패했습니다.",
                "result": {
        	         {error.details} || null
                }
            }
            ```
        ```
        
- **`[GET]` 알림 전체 조회**
    
    
    | **요구사항 ID** | **ALAM_001, ALAM_002, MEMBER_PAGE_005** |
    | --- | --- |
    | Description | 지원자는 자신에게 도착한 알림을 전체 조회한다. |
    | URL | /api/alarm/read-all |
    | Auth Required | Yes |
    
    | **`body params`** | **`요구 사항 상세`** |
    | --- | --- |
    |  | **[알림 전체 조회]**
    1. 지원자는 자신에게 도착한 알림을 전체 조회한다.
    2. 채용담당자와의 면접 일정 조율 알림
    3. 지원한 서류의 합격 여부 알림
    4. 확정된 면접 일정(1차, 2차)에 대한 알림
    5. 최종 결과에 대한 알림 |
    - **✅ Response 200**
        
        ```json
            ```json
            {
                "code": "200",
                "isSuccess": true,
                "message": "알람 전체 조회에 성공했습니다.",
                "result": {
        		        [
        						   "type" : "알림 타입",
        						   "status" : 알림 조회 여부,
        						   "message" : "알림 메시지",
        						   "url" : "알림 url",
        						   "createdAt" : "등록 날짜",
        						   "interviewScheduleIdx" : 1
        		        ],
        
            }
            ```
        ```
        
    - ❌ **error Response 300**
        
        ```json
            ```json
            {
                "code": "300",
                "isSuccess": false,
                "message": "알람 전체 조회에 실패했습니다.",
                "result": {
        	         {error.details} || null
                }
            }
            ```
        ```