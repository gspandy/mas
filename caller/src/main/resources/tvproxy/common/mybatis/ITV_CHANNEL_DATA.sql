create table ITV_CHANNEL_DATA
(
  channel_id int not null,
  title varchar(20) not null,
  title_datatype int,
  title_bgcolor varchar(10),
  title_channelid int,
  title_searchcondition varchar(30),
  title_albumid int,
  data_source int,
  data_url varchar(300),
  data_preloadtype int,
  data_preloadsize int,
  order_num int not null,
  
  key (channel_id)
);
