USE `ecom`;
update message set MESSAGE_BUNDLE_COUNT = 99900,PACKAGE_START_DATE = CURRENT_DATE,PACKAGE_END_DATE = '2020-10-15',PACKAGE_RENEW_DATE = '2020-10-15',
MESSAGE_TEXT_LIMIT = 0,VENDER_NAME = 'EOCEAN',MASK_NAME = 'Kites%20Kids',USER_ID = 'Kites%20Kids',PASSWORD = 'AE5fOuwKBv4MzNqo0K7eOcHeEoLKpsdzBzP828y6zG5RYiDDDES7cNA%2fHGOeeTn3sg%3d%3d' WHERE MESSAGE_ID = 1;

truncate  MESSAGE_DETAIL;