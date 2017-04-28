SELECT customerName, address, address2, city, postalCode, country, phone
FROM customer AS cu, address AS ad, city, country AS co
WHERE cu.addressId = ad.addressId
AND ad.cityId = city.cityId
AND city.countryId = co.countryId
AND active = 1