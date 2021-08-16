<?php 

    $con = mysqli_connect("localhost", "lounge", "dlrudals", "lounge");
    $result = mysqli_query($con, "SELECT * FROM List WHERE pcID = 3 ORDER BY date, startTime");

    $response = array();//배열 선언

    while($row = mysqli_fetch_array($result)){
        //response["userID"]=$row[0] ....이런식으로 됨.

        array_push($response, array("date"=>$row[3],"startTime"=>$row[4], "endTime"=>$row[5]));
    }
    //response라는 변수명으로 JSON 타입으로 $response 내용을 출력

    echo json_encode(array("response"=>$response));

    mysqli_close($con);
?>