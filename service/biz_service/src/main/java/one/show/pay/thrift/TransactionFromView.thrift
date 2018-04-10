namespace java one.show.pay.thrift.view

struct TransactionFromView{  
    1:string id
	2:i32 giveItemId
	3:string giveItemName
	4:i32 giveItemType
	5:double giveItemNumber
	6:i32 actuallyItemId
	7:string actuallyItemName
	8:i32 actuallyItemType
	9:double actuallyItemNumber
	10:double actuallyItemPrice
	11:i32 actionType
	12:string reason
	13:i64 uid
	14:i32 createTime
	15:i64 toUid
	16:string toProfileImg
	17:string toNickName
} 
