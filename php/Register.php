<?php 

    $con = mysqli_connect("localhost", "lounge", "dlrudals", "lounge");
    mysqli_query($con,'SET NAMES utf8');

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : '';
    $userName = isset($_POST["userName"]) ? $_POST["userName"] : '';
    $userPhNum = isset($_POST["userPhNum"]) ? $_POST["userPhNum"] : '';

    $statement = mysqli_prepare($con, "INSERT INTO USER VALUES (?,?,?)");
    mysqli_stmt_bind_param($statement, "sss", $userID, $userName, $userPhNum);
    mysqli_stmt_execute($statement);

    
    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);



?>