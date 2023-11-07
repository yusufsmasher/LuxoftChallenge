# LuxoftChallenge
Luxoft Challenge

Have Created The API in TrnasferBetweenAccount Coontroller

Its A POST API expecting 3 Parameters as Json From account Id, To Account ID and Amount  

Sample Request Json 

{
"fromAccountID":"Id-123",
"amount":1000,
"toAccountID":"Id-124"
}

Sample Response Jason
Sucess Response
{
    "status": "Success",
    "error": null
}
Failure Response
{
    "status": "Failure",
    "error": "In Sufficient Balance in From Account ID: Id-123"
}

For Thread Safe Implementation Have Synchronized the Service Method call and the Actuall Bloclk of code where the amount is gonna be deducted from the From Account. Also Made the API Scope To Session as well. 

Have handled possible all scenarios which are 
1. Account ID Given in From or Too is Null or Empty or the Account Is not Present
2. Amount to Be deucted is Greater than Current Balance or non Postive or 0

Have Done Junit Testing as Well covering all Negative Scenario as Well both In Controller and Service Class, by Mocking the Depednecy Class of them and follwoing correct Functional Testing Concept. 

Also Did Integration testing through Rest Client Post Man. 

