(function(){
	var app = angular.module('stockMarket',[]);
	var activeUserID;

	app.controller('PanelController', function(){
		
		this.tab=1;
		
		this.selectTab = function(setTab){
			this.tab=setTab;
		};

		this.isSelected = function(checkTab){
			return this.tab ===checkTab
		};
	});

	app.controller('StockMarketController' , [ '$http', function($http){
		this.newUserID;
		this.products = symbols;
		this.info = info;
		this.quantity;
		this.price;

		var that = this;
		this.buy = function(symbol,type){
			if (that.quantity === 0 || that.price===0 || that.quantity===null || that.price===null )
				return;
			that.info.orders.push({symbolID:symbol,price:that.price,quantity:that.quantity,type:type});
			
			for(var i = that.market.length - 1; i >= 0; i--) {
			    if(that.market[i].id === symbol) {
			    	that.market[i].sellQueue.push({price:that.price,quantity:that.quantity});
			    }
			}		
		};

		this.loginUser = function(argument) {
			activeUserID = that.newUserID;
		}

		$http.get('symbols.json').success(function(data) {
			console.log(data);
			that.market = data;
		});
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

	var symbols =[
		{
			id:'RENA',
			type:'GTC',
		},
		{
			id:'DD',
			type:'MPO',
		},
		{
			id:'Oo',
			type:'IOC',
		},
	];

	var shares =[
		{
			id:'RENA',
			sellQueue:[
				{
					price:100,
					quantity: 4,
				},
			],
			buyQueue:[
				{
					price:200,
					quantity: 14,
				},
			],
		},
	];

	app.controller('UserController', function(){
		this.info = info;
	});

	var info = {
		ID: 12345,
		name: 'ame',
		lastName: 'ghezi',
		credit: 500,
		shares:[
			{
				symbolID:123,
				quantity:5,
			}
		], 
		orders:[
			{
				symbolID:456,
				price:100,
				quantity:3,
				type:'type',
			}
		],
		image:{
			full:'./stuff/1.jpg',
			thumb:'./stuff/1.jpg'
		},
	}
	
} )();
