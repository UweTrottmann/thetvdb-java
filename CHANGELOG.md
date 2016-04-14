Change Log
==========

## 1.0.2

_2016-04-14_

* Do not retry `/login` requests, fail immediately if `401 Unauthorized` is returned.

## 1.0.1

_2016-04-09_

* Remove deprecated `seriesId` from `Series` entity. Would return empty string instead of integer if not available, so
  remove it altogether. 

## 1.0.0

_2016-04-08_

* Initial release supporting TheTVDB API v2.0.0 final.
* Many endpoints are still incomplete, pull requests are welcome.