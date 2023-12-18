-- Create a schema
CREATE SCHEMA hotel;

-- Set the search path to the new schema
SET search_path TO hotel;

-- Create an extension in a separate transaction block
-- Otherwise, it might not be effective in the same transaction context as the subsequent table creation
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_extension WHERE extname = 'uuid-ossp') THEN
        CREATE EXTENSION "uuid-ossp";
    END IF;
END $$;

-- Create the "user" table
CREATE TABLE "user" (
    user_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    active bool NULL
    -- Other fields
);

-- Create the "hotel" table
CREATE TABLE hotel (
    hotel_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    hotel_name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    room_type VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
    -- Other fields
);

-- Create the "booking" table
CREATE TABLE booking (
    booking_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_id UUID,
    hotel_id UUID,
    checkin_date DATE NOT NULL,
    checkout_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user"(user_id),
    FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id)
);

-- Create user_role table
CREATE TABLE user_role (
    username VARCHAR(255) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    PRIMARY KEY (username, authority)
);
