var app = angular.module("zipCodeAnalyser", []);
//app.controller("inputdataCntlr",inputdataCntlr);
app.controller('inputdataCntlr', function($scope, $log, $http) {
	$scope.zipcodeRanges = [];

	$scope.addNew = function() {
		if ($scope.action == "add") {
			var obj = {
				'lowerBound' : '',
				'upperBound' : ''
			};
			$scope.zipcodeRanges.push(obj);
		}

		$scope.action = "add";

	};

	$scope.remove = function() {
		var newDataList = [];
		$scope.selectedAll = false;
		angular.forEach($scope.zipcodeRanges, function(selected) {
			if (!selected.selected) {
				newDataList.push(selected);
			}
		});
		$scope.zipcodeRanges = newDataList;
		if ($scope.zipcodeRanges.length == 0) {
			$scope.addNew();
		}

	};

	$scope.checkAll = function() {
		angular.forEach($scope.zipcodeRanges, function(zipcodeRange) {
			zipcodeRange.selected = $scope.selectedAll;
		});
	};

	$scope.validateRange = function() {
		var vonExtensionList = [], params = {};
		var zipCodeRanges = $scope.zipcodeRanges;
		params.zipCodeRanges = zipCodeRanges;
		$http.post(window.location.origin+"/rest/zipcode/validate", params).then(
				function(response) {
					$scope.content = response.data;
					$scope.statuscode = response.status;
					$scope.statustext = response.statusText;
					$scope.finalRanges = response.data.zipCodeRanges;
				})
				$scope.action="";
	};

	function init() {
		$scope.row = $scope.ngDialogData;
		$scope.zipcodeRanges = [];
		$scope.finalRanges = [];
		$scope.action="add";
		$scope.addNew();
	}

	init();
});