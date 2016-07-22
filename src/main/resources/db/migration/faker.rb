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

      arrayOfSqlStatements = Array.new
      x.times do |x|
         arrayOfSqlStatements << "INSERT INTO products (name, code, price, description,chargeCard) VALUES ('#{products[x].name}' , '#{products[x].description}', '#{products[x].chargeCard}', #{products[x].code}, #{products[x].price});"
      File.open('V2__insert_products.sql', 'w') {|f| f.write arrayOfSqlStatements.join("\n")}
      end 
    puts "Done, writing to file. "
  end 
    	

   def self.makeProduct(x)
   	products = Array.new
   	x.times do |n|
   		products.push(Products.new(Faker::Commerce.product_name, Faker::Code.isbn, Faker::Commerce.price, Faker::Lorem.words(2),Faker::Business.credit_card_type))
   	end 
   		createSQL(products, x)
   end 


   Products.makeProduct(100)




end 