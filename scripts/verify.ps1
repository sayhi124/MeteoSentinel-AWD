$ErrorActionPreference='Stop'
python (Join-Path $PSScriptRoot 'verify.py')
if($LASTEXITCODE -ne 0){throw 'Vérification échouée'}
