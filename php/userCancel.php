<?php 

    $con = mysqli_connect("localhost", "lounge", "dlrudals", "lounge");
    mysqli_query($con,'SET NAMES utf8');

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : '';
    $delete = mysqli_prepare($con, "DELETE FROM List WHERE userID = ?");
    
    $statement = mysqli_prepare($con, "DELETE FROM USER WHERE userID = ?");
    mysqli_stmt_bind_param($delete, "s", $userID);
    mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($delete);
    mysqli_stmt_execute($statement);
    



    $response = array();
    $response["success"] = true;
 

    echo json_encode($response);



?>