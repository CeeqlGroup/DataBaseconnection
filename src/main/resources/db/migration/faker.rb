require 'faker'
require 'pry'

class Product 
	attr_accessor :name, :description,:chargeCard, :code,:price

   def initialize(name,description,chargeCard,code, price)
		@name = name
    @description = description
    @chargeCard = chargeCard
    @code = code 
		@price = price
   end 

    def self.createSQL(products,x)
      arrayOfSqlStatements = Array.new
      x.times do |x|
         arrayOfSqlStatements << "INSERT INTO products (name,description,chargeCard,code, price) VALUES ('#{products[x].name}','#{products[x].description}','#{products[x].chargeCard}', '#{products[x].code}', #{products[x].price});" 
      File.open('V2__insert_products.sql', 'w') {|f| f.write arrayOfSqlStatements.join("\n")}
      end 
    puts "Done, writing to file. "
  end 
    	
   def self.makeProduct(x)
   	products = Array.new
    x.times do 
    products.push(Product.new(Faker::Commerce.product_name, Faker::Lorem.words(2),Faker::Business.credit_card_type, Faker::Code.isbn, Faker::Commerce.price))

   end 
   

   		createSQL(products, x)
   end 


   Product.makeProduct(3)




end 