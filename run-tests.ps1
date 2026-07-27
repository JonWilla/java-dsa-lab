$ErrorActionPreference = "Stop"

$sources = Get-ChildItem -Path "src" -Recurse -Filter "*.java" |
    Select-Object -ExpandProperty FullName

if (-not $sources) {
    throw "No Java source files were found under src."
}

New-Item -ItemType Directory -Path "out" -Force | Out-Null

Write-Host "Compiling all Java source files..."
javac -d out $sources

if ($LASTEXITCODE -ne 0) {
    throw "Compilation failed."
}

Write-Host "`nRunning complete regression suite..."
java -cp out AllTests

if ($LASTEXITCODE -ne 0) {
    throw "A test suite failed."
}

Write-Host "`nRunning integrated demonstration..."
java -cp out Main

if ($LASTEXITCODE -ne 0) {
    throw "Integrated demonstration failed."
}

Write-Host "`nBuild, tests, and demonstration completed successfully."
