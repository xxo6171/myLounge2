<?php 

    $con = mysqli_connect("localhost", "lounge", "dlrudals", "lounge");
    mysqli_query($con,'SET NAMES utf8');
    $userName = isset($_POST["userName"]) ? $_POST["userName"] : '';
    $userID = isset($_POST["userID"]) ? $_POST["userID"] : '';

    $statement = mysqli_prepare($con, "UPDATE USER SET userName = ? WHERE userID = ?");
    mysqli_stmt_bind_param($statement, "ss", $userName, $userID);
    mysqli_stmt_execute($statement);



    $response = array();
    $response["success"] = true;
 

    echo json_encode($response);



?>