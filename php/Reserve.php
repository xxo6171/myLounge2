<?php 

   $con = mysqli_connect("localhost", "lounge", "dlrudals", "lounge");
    mysqli_query($con, 'SET NAMES utf8');

$listID = (isset($_POST['listID']) ? $_POST['listID'] : null);
$userID = (isset($_POST['userID']) ? $_POST['userID'] : null);
$pcID = (isset($_POST['pcID']) ? $_POST['pcID'] : null);
$date = (isset($_POST['date']) ? $_POST['date'] : null);
$startTime = (isset($_POST['startTime']) ? $_POST['startTime'] : null);
$endTime = (isset($_POST['endTime']) ? $_POST['endTime'] : null);

$delete_query = "DELETE FROM List WHERE listID = (SELECT MAX(listID) FROM (SELECT listID FROM List)tmp);";

$select_query = "SELECT *FROM List A WHERE
(SELECT count(*) from List B where  A.pcID = B.pcID AND A.date = B.date AND
((A.startTime BETWEEN B.startTime AND B.endTime) or
(A.endTime BETWEEN B.startTime AND B.endTime))) > 1;";
$response = array();
$response["success"] = false;

$statement = mysqli_prepare(
    $con,
    "INSERT INTO List(listID, userID,pcID,date,startTime,endTime) VALUES (?,?,?,?,?,?);"
);
  
mysqli_stmt_bind_param(
    $statement,
    "isssss",
    $listID,
    $userID,
    $pcID,
    $date,
    $startTime,
    $endTime
);
mysqli_stmt_execute($statement); 
$state = mysqli_query($con, $select_query);
$count = mysqli_num_rows($state);


   
if($count > 0) {
$response["success"] = false;
mysqli_query($con, $delete_query);
}
else{
$response["success"] = true;
}
 echo json_encode($response);
?>