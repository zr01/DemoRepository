var magic8Ball = angular.module('magic8Ball', ['base64']);

magic8Ball.controller('main', ['$scope', '$http', '$base64',
	function($scope, $http, $base64){
		$scope.ask = function(){
			var question = $scope.question;
			if(question.length == 0){
				return;
			}
			
			question = $base64.encode(question);
			
			//Submit to REST API
			var result = $http.get('/actions/askthyquestion?question=' + question);
			
			result.success(function(response){
				var type = response.type;
				$scope.answer = response.answer;
				
				if(type == 'yes'){
					$scope.alertType = 'alert-success';
					
				}else if(type == 'no'){
					$scope.alertType = 'alert-danger';
				}else{
					$scope.alertType = 'alert-warning';
				}
			});
			
		}
	}
]);