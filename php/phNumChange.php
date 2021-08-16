<?php 
 
    $con = mysqli_connect("localhost", "lounge", "dlrudals", "lounge");
    mysqli_query($con,'SET NAMES utf8');
    $userPhNum = isset($_POST["userPhNum"]) ? $_POST["userPhNum"] : '';
    $userID =isset($_POST["userID"]) ? $_POST["userID"] : '';

    $statement = mysqli_prepare($con, "UPDATE USER SET userPhNum = ? WHERE userID = ?");
    mysqli_stmt_bind_param($statement, "ss", $userPhNum, $userID);
    mysqli_stmt_execute($statement);



    $response = array();
    $response["success"] = true;
 

    echo json_encode($response);



?>