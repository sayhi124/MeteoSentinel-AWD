param([switch]$SkipBuild)
$ErrorActionPreference='Stop'
$projectRoot=Split-Path $PSScriptRoot -Parent
Set-Location -LiteralPath $projectRoot
foreach($port in @(8180,8211,8212,8213,8214,8215,8216,8217,8762,8889)) {if(Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue){throw "Port $port occupé."}}
if(-not $SkipBuild){ & mvn '-B' 'verify'; if($LASTEXITCODE -ne 0){throw 'Échec compilation/tests'} }
New-Item -ItemType Directory -Force -Path '.run' | Out-Null
function Start-Module($name){
 $jar=[IO.Path]::GetFullPath((Join-Path $projectRoot "Backend/$name/target/$name-1.0.0-SNAPSHOT.jar"))
 $proc=Start-Process -FilePath 'java' -ArgumentList @('-Xms24m','-Xmx160m','-XX:ActiveProcessorCount=2','-jar',('"'+$jar+'"')) -WindowStyle Hidden -WorkingDirectory $projectRoot -PassThru -RedirectStandardOutput ".run/$name.log" -RedirectStandardError ".run/$name.err.log"
 @{id=$proc.Id;jar=$jar}|ConvertTo-Json -Compress|Add-Content '.run/processes.jsonl'
}
function Wait-Healthy($port){
 for($i=0;$i -lt 90;$i++){try {$r=Invoke-RestMethod "http://127.0.0.1:$port/actuator/health";if($r.status -eq 'UP'){return}}catch{};Start-Sleep -Seconds 1};throw "Port $port non prêt : voir .run/"
}
Start-Module 'config-server'; Wait-Healthy 8889
Start-Module 'eureka-server'; Wait-Healthy 8762
foreach($name in @('weather-service','forecast-service','alert-service','air-quality-service','user-service','activity-service','datahub-service','api-gateway')){Start-Module $name}
Write-Host 'Portail de conception : http://127.0.0.1:8180. Laisser les services démarrer, puis lancer scripts/verify.ps1.'
