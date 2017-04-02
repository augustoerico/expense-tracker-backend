package io.github.augustoerico.api

class AuthenticatedApiSpec extends ApiSpec {

    @Override
    def setupContextSpec() {
        def body = [username: 'erico', password: 'erico-password']
        def response = restClient.post path: '/sign_in', body: body

        def token = response.responseData?.token
        assert token

        restClient.setHeaders([Authorization: "Bearer $token"])
    }

}
