
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
			if (this.quantity === 0 || this.price===0 )
				return;
			else
				this.info.orders.push({symbolID:symbol,price:this.price,quantity:this.quantity,type:type});
		};
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
			id:1,
			type:'GTC',
		},
		{
			id:2,
			type:'MPO',
		},
		{
			id:3,
			type:'IOC',
		},
	];

	var stock =[
		{
			instrument:'instrument',
			price: 2,
			quantity: 5,
			type: 'type',
			canPurchase: true,
			soldOut: false,
			image:{
				full:'1.jpg',
				thumb:'1.jpg'
			},
		},
		{
			instrument:'instrument1',
			price: 2,
			quantity: 5,
			type: 'type',
			canPurchase: false,
			soldOut: false,
		},
		{
			instrument:'instrument2',
			price: 2,
			quantity: 5,
			type: 'type',
			canPurchase: true,
			soldOut: true,
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
