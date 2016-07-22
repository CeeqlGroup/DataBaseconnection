require 'faker'
require 'pry'

class Products 
	attr_accessor :name, :code,:price,:description,:chargeCard

   def initialize(name, code,price,description,chargeCard)
		@name = name
		@code =  code
		@price = price
		@description = description
		@chargeCard = chargeCard

   end 

    def self.createSQL(products,x)
    	
    x.times do |x|
    	insertStatement = "INSERT INTO products (Name,Code,Price,Description,ChargeCard) VALUES + (" + "#{products[x].name}" + "," + "#{products[x].code}" + "," + "#{products[x].price}" + "," + "#{products[x].description}" + "," + "#{products[x].chargeCard}" 

				puts insertStatement

    	end 



 

 end 
    	
 

   def self.makeProduct(x) 
   	products = Array.new

   	x.times do |n|
   		products.push(Products.new(Faker::Commerce.product_name,Faker::Code.isbn,Faker::Commerce.price,Faker::Lorem.words(2),Faker::Business.credit_card_type))

   	end 
   		createSQL(products, x)
   end 



   Products.makeProduct(10)




end 