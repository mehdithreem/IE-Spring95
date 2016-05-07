
(function(){
	
	var app = angular.module('stockMarket',[]);

	app.controller('PanelController', function(){
		
		this.tab=1;
		
		this.selectTab = function(setTab){
			this.tab=setTab;
		};

		this.isSelected = function(checkTab){
			return this.tab ===checkTab
		};
	});

	app.controller('StockMarketController' , function(){
		this.products = symbols;
		this.info = currUser;
		this.quantity;
		this.price;
		this.buy = function(symbol,type){
			if (this.quantity === 0 || this.price===0 || this.quantity===null || this.price===null )
				return;
			this.info.orders.push({symbolID:symbol,price:this.price,quantity:this.quantity,type:type});
			
			for(var i = this.market.length - 1; i >= 0; i--) {
			    if(this.market[i].id === symbol) {
			    	this.market[i].sellQueue.push({price:this.price,quantity:this.quantity});
			    }
			}		
		};
		this.market = shares;
	});

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
		this.info = currUser;
		
		this.acceptOrder = function(symbolID){
			for(var i = this.info.orders.length - 1; i >= 0; i--) {
			    if(this.info.orders[i].symbolID === symbolID) {
			    	this.info.acceptOrders.push(this.info.orders[i]);
			    	this.info.orders.splice(i, 1);
			    }
			}
		};

		this.rejectOrder = function(symbolID){
			for(var i = this.info.orders.length - 1; i >= 0; i--) {
			    if(this.info.orders[i].symbolID === symbolID) {
			    	this.info.rejectOrders.push(this.info.orders[i]);
			    	this.info.orders.splice(i, 1);
			    }
			}
		};

	});

	var currUser = {
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
			full:'1.jpg',
			thumb:'1.jpg'
		},
	}
	
} )();
