;
(function() {
	'use strict',
	angular.module('gateway').factory('auth', AuthenticationService);
	
	AuthenticationService.$inject = ['$http'];
	
	function AuthenticationService($http) {
		var auth = {
			authenticated: false,
			authenticate: authenticate
		}
		
		function authenticate(credentials, callback) {
			var headers = credentials && credentials.username ? {
				authorization: 'Basic '
					+ btoa(credentials.username + ':' + credentials.password)
			} : {};
			
			return $http.get('user', {headers: headers})
				.then(function(response) {
					if (response.data.name) {
						auth.authenticated = true;
					} else {
						auth.authenticated = false;
					}
					callback && callback(auth.authenticated);
				}, function(response) {
					auth.authenticated = false;
					callback && callback(false);
				});
		}
		
		return auth;
	}
	
})();