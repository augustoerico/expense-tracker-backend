package io.github.augustoerico.api

class AuthenticatedApiSpec extends ApiSpec {

    def credentials = [username: 'erico', password: 'erico-password']

    @Override
    def setupContextSpec() {
        def body = credentials
        def response = restClient.post path: '/sign_in', body: body

        def token = response.responseData?.token
        assert token

        restClient.setHeaders([Authorization: "Bearer $token"])
    }

}
