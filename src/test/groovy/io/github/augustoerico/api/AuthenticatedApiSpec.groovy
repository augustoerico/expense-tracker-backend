package io.github.augustoerico.api

abstract class AuthenticatedApiSpec extends ApiSpec {

    static final String TOKEN = 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfaWQiOiIxIiwidXNlcm5hbWUiOiJlcmljbyIs' +
            'InBhc3N3b3JkIjoiaFdaY2RGaVBvNjl1bTNaQUhHQURENnpsTm1XeGlpTHVHU090aEs2Y3ZIST0iLCJ0eXBlIjoiUkVHVUxBUiIsInBl' +
            'cm1pc3Npb25zIjpbInJlZ3VsYXIiXSwiaWF0IjoxNDkxNjY1MzM2fQ==.aQpKRgaws_CKBoKmJ2O6JphpzkUcRuYWRsda_A_4QrM='

    @Override
    def runBefore() {
        restClient.setHeaders([Authorization: TOKEN])
    }

}
