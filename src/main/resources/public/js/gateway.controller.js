;
(function() {
	'user strict';
	angular.module('gateway').controller('GatewayCtrl', GatewayController);
	
	GatewayController.$inject = ['auth', '$window'];
	
	function GatewayController(auth, $window) {
		var vm = this;
		vm.credentials = {};
		vm.error = false;
		vm.login = login;
		
		activate();
		
		function activate() {
			
		}
		
		function authenticated() {
			return auth.authenticated;
		}
		
		function login(valid) {
			auth.authenticate(vm.credentials, callback);
		}
		
		function reset(form) {
			if (form) {
				form.$setPristine();
				form.$setUntouched();
			}
		}
		
		function callback(authenticated) {
			if (!authenticated) {
				vm.error = true;
				vm.credentials = {};
			} else {
				$window.location.href += 'ui/';
			}
		}
		
	}
})();