
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
		this.products = stock;
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
				buyer:'daE',
				type:'type',
			}
		],

		acceptOrders:[
			{
				symbolID:456,
				price:100,
				quantity:1,
				buyer:'mamad',
				type:'type',
			}
		],

		rejectOrders:[
			{
				symbolID:456,
				price:100,
				quantity:2,
				owner:'nader',
				type:'type',
			}
		],

		image:{
			full:'1.jpg',
			thumb:'1.jpg'
		},
	}
	
} )();
