<?php
    
    $con = mysqli_connect("localhost", "lounge", "dlrudals", "lounge");
    mysqli_query($con,'SET NAMES utf8');

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : '';
    $userName = isset($_POST["userName"]) ? $_POST["userName"] : '';
    
    $statement = mysqli_prepare($con, "SELECT * FROM USER WHERE userID = ? AND userName = ?");

    mysqli_stmt_bind_param($statement, "ss", $userID, $userName);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $userName, $userPhNum);

    $response = array();
    $response["success"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userID"] = $userID;
        $response["userName"] = $userName;
        $response["userPhNum"] = $userPhNum;        
    }
    
    echo json_encode($response);



?>