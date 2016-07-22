require 'faker'
require 'pry'

class Products 
	attr_accessor :name, :description,:chargeCard, :code,:price

   def initialize(name, description, chargeCard, code,price)
		@name = name
		@code =  code
		@price = price
		@description = description
		@chargeCard = chargeCard

   end 

    def self.createSQL(products,x)

    x.times do |x|
    insertStatement =  "INSERT INTO products (name, code, price, description,chargeCard) VALUES ('#{products[x].name}' , '#{products[x].description}', '#{products[x].chargeCard}', #{products[x].code}, #{products[x].price});"


				puts insertStatement
  end 
  

 end 
    	
 

   def self.makeProduct(x) 
   	products = Array.new

   

   	x.times do |n|
   		products.push(Products.new(Faker::Commerce.product_name,Faker::Lorem.words(2),Faker::Business.credit_card_type, Faker::Code.isbn,Faker::Commerce.price))

   	end 
   		createSQL(products, x)
   end 



   Products.makeProduct(10)




end 