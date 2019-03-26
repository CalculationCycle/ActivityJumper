<?php

$travelSpeeds = array();
$tr = array();
$tr['Way'] = 'Foot';
$tr['Speed'] = 5.0;
$travelSpeeds[] = $tr;
$tr = array();
$tr['Way'] = 'Bike';
$tr['Speed'] = 20.0;
$travelSpeeds[] = $tr;
$tr = array();
$tr['Way'] = 'Car';
$tr['Speed'] = 90.0;
$travelSpeeds[] = $tr;
$tr = array();
$tr['Way'] = 'Falcon';
$tr['Speed'] = 1000.0;
$travelSpeeds[] = $tr;
$tr = array();
$tr['Way'] = 'Motorcycle';
$tr['Speed'] = 130.0;
$travelSpeeds[] = $tr;
$tr = array();
$tr['Way'] = 'Slow Walk';
$tr['Speed'] = 3.0;
$travelSpeeds[] = $tr;

$capitals = array();
$cap = array();
$cap['Name'] = 'Berlin';
$cap['Lat'] = '52.30N';
$cap['Long'] = '13.25E';
$capitals[] = $cap;
$cap = array();
$cap['Name'] = 'Stockholm';
$cap['Lat'] = '59.20N';
$cap['Long'] = '18.03E';
$capitals[] = $cap;


$dataToSend = array();
$dataToSend['travelSpeeds'] = $travelSpeeds;
$dataToSend['capitals'] = $capitals;

header('Content-Type: application/json');
echo json_encode($dataToSend);

?>
