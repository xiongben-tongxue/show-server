namespace java one.show.pay.thrift.view

struct TransactionToView{
    1:string id
	2:i32 receiveItemId
	3:string receiveItemName
	4:i32 receiveItemType
	5:double receiveItemNumber
	6:i32 actuallyItemId
	7:string actuallyItemName
	8:i32 actuallyItemType
	9:double actuallyItemNumber
	10:double actuallyItemPrice
	11:i32 actionType
	12:string reason
	13:string vid
	14:i64 uid
	15:i32 createTime
	16:i64 fromUid
} 
