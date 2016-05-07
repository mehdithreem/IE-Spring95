(function(){
	var app = angular.module('stockMarket',[]);

	app.controller('PanelController', function(){
		this.tab=1;
		
		this.selectTab = function(setTab){
			this.tab=setTab;
		};

		this.isSelected = function(checkTab){
			return this.tab === checkTab
		};
	});

	app.controller('StockMarketController' , [ '$http', function($http){
		this.activeUser = 1;

		var that = this;
		this.sellOrBuy = function(address,symbol,quan,value){
			console.log(symbol+quan+value);
			if (quan === 0 || value===0 || quan===null || value===null)
				return;
			
			$http.get(address, 
				{params:{id : that.activeUser, instrument : symbol, type : "GTC", price : value, quantity : quan}}
				).then(function(response) {
				console.log(response.data);
				if (response.status === 203) {
					that.message = response.data;
				} else {
					that.message = response.data;
					setTimeout(function(argument) {
						angular.element(".buysymboldialog").modal("hide");
						that.message = "";
						that.initMarketTimer();
					}, 2000);
				}
			});
		};


		this.loginUser = function(argument) {
			that.activeUser = argument;
			that.updateUser();
		}

		this.updateUser = function () {
			$http.get('user.json', {params: {id : that.activeUser}}).then(function(response) {
				console.log(response.data);
				if (response.status === 203) {
					that.activeUser = "UserNotFound";
					that.info = undefined;
				} else {
					that.info = response.data;
				}
			});
		};

		this.refreshtMarket = function () {
			$http.get('symbols.json').success(function(data) {
				console.log(data);
				that.market = data;
			});
		};

		this.initMarketTimer =  function(argument) {
			that.marketTimer = setInterval(this.refreshtMarket, 1000*15);
		}

		this.clearMarketTimer =  function(argument) {
			clearInterval(that.marketTimer);
		}

		this.refreshtMarket();
		this.updateUser();
		this.initMarketTimer();
		setInterval(this.updateUser, 1000*5);
	}]);

	app.controller('ListController', function(){
		this.tab=0;
		this.selectTab = function(){
			if (this.tab === 1)
				this.tab = 0;
			else
				this.tab=1;
		};

		this.isSelected = function(){
			return this.tab ===1;
		};
	});
} )();
