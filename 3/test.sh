# curl "http://localhost:9091/order/sell?id=1&instrument=RENA1&price=130&quantity=40&type=GTC"
# curl "http://localhost:9091/customer/add?id=2&name=A&family=B"
# curl "http://localhost:9091/customer/deposite?id=2&amount=200"
# curl "http://localhost:9091/order/buy?id=2&instrument=RENA1&price=140&quantity=1&type=IOC"

# curl "http://localhost:9091/customer/add?id=2&name=A&family=A"
# curl "http://localhost:9091/customer/add?id=3&name=B&family=A"
# curl "http://localhost:9091/customer/add?id=4&name=A&family=A"
# curl "http://localhost:9091/customer/add?id=1&name=A&family=A" #----> error repeated id
# curl "http://localhost:9091/customer/deposit?id=2&amount=100"
# curl "http://localhost:9091/customer/deposit?id=3&amount=200"
# curl "http://localhost:9091/customer/withdraw?id=4&amount=100" #---> error not enough money
# curl "http://localhost:9091/order/sell?id=1&instrument=RENA1&price=130&quantity=1&type=GTC"
# curl "http://localhost:9091/order/buy?id=2&instrument=RENA1&price=130&quantity=1&type=GTC" #---> error not enough money
# curl "http://localhost:9091/order/buy?id=3&instrument=RENA1&price=130&quantity=1&type=GTC"
# curl "http://localhost:9091/order/sell?id=1&instrument=RENA1&price=130&quantity=1&type=IOC"
# curl "http://localhost:9091/order/sell?id=1&instrument=RENA1&price=130&quantity=1&type=MOP"
# curl "http://localhost:9091/order/sell?id=3&instrument=RENA1&price=80&quantity=1&type=GTC"
# curl "http://localhost:9091/order/buy?id=2&instrument=RENA1&price=80&quantity=1&type=GTC"

curl "http://localhost:9091/customer/add?id=2&name=A&family=A"
curl "http://localhost:9091/customer/add?id=3&name=B&family=A"
curl "http://localhost:9091/customer/add?id=4&name=A&family=A"
curl "http://localhost:9091/customer/add?id=1&name=A&family=A" # ----> error repeated id
curl "http://localhost:9091/customer/deposit?id=2&amount=200"
curl "http://localhost:9091/customer/deposit?id=3&amount=200"
curl "http://localhost:9091/customer/deposit?id=4&amount=200"
curl "http://localhost:9091/order/sell?id=1&instrument=RENA1&price=130&quantity=1&type=GTC"
curl "http://localhost:9091/order/buy?id=2&instrument=RENA1&price=130&quantity=1&type=GTC"
curl "http://localhost:9091/order/buy?id=3&instrument=RENA1&price=150&quantity=2&type=GTC"
curl "http://localhost:9091/order/buy?id=4&instrument=RENA1&price=160&quantity=1&type=GTC"
curl "http://localhost:9091/order/buy?id=2&instrument=RENA1&price=140&quantity=1&type=GTC"
curl "http://localhost:9091/order/sell?id=1&instrument=RENA1&price=130&quantity=2&type=GTC"