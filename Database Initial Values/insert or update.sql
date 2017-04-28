INSERT INTO customer VALUES (8, 'Doris Belle', 2, 0, NOW(), 'Aaron', NOW(), 'Aaron')
ON DUPLICATE KEY UPDATE customerName='Doris Belle', addressId=2, lastUpdate=NOW(), lastUpdateBy='Aaron'