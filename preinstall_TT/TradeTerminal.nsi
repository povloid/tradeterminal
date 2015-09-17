;имя приложения
!define PRODUCT_NAME "TradeTerminal"
;версия приложения
!define PRODUCT_VERSION "1.3.1"
;папка, где будут храниться исходные файлы, подлежащие сжатию.
!define pkgdir "TT"


!include "MUI2.nsh"
!include "Sections.nsh"
!include "LogicLib.nsh"
!include "Memento.nsh"
!include "WordFunc.nsh"

; ип компресии
SetCompressor /SOLID lzma

; Константа MUI_ABORTWARNING определяет, выдавать ли предупреждение 
; при закрытии инсталятора пользователем. По типу такого: «Вы действительно 
; хотите прервать установку ..бла бла » и кнопки «да» и «нет».
!define MUI_ABORTWARNING

;; Константа MUI_ICON определяет значок инсталятора: 
;; !define MUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\modern-install.ico"
;; Здесь ${NSISDIR} - системная константа, означает путь к папке, куда установлен сам NSIS.
;; Предопределенных констант несколько:



;Далее необходимо определиться со страницами, которые будут видны конечному пользователю.
;Они определяются через вызов макросов - логически завершенных блоков кода, определенных,
; как несложно догадаться, в файле MUI.nsh.
;В данном примере нам потребуются
;    * Страница приветствия
;    * Страница прогресса выполнения инсталлятора
;    * Страница завершения работы
;Соответственно, необходимо вставить следующие строки в наш скрипт:


!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_LICENSE "${pkgdir}\License.txt"
!insertmacro MUI_PAGE_COMPONENTS
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH

!insertmacro MUI_UNPAGE_WELCOME
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES




;Если мы хотим, чтобы все страницы нашего скрипта были на русском языке, необходимо также добавить вызов макроса локализации:
!insertmacro MUI_LANGUAGE "Russian"


;Запущенный инсталлятор должен иметь заголовок, отражающий его назначение, 
;такой как, например, «Установка Microsoft Office». Этого можно добиться, используя две следующих инструкции:
;    * Команда Name задает название кнопки инсталлятора на панели задач. В нашей случае мы будем использовать её в следующем виде:

Name "${PRODUCT_NAME}"

;    * Команда Caption задает заголовок окна инсталятора. Её мы зададим равной

Caption "Установка ${PRODUCT_NAME} ${PRODUCT_VERSION}"

;Имя файла инсталлятора задается командой OutFile. Вот так:
OutFile "${PRODUCT_NAME}-${PRODUCT_VERSION}.exe"
InstallDir "$PROGRAMFILES\TT"


;Следующая команда, ShowInstDetails, говорит компилятору, 
;что в окне хода выполнения инсталлятора должен быть виден 
;протокол его действий. По умолчанию он скрыт, и появляется 
;только по нажатию кнопки «Детали». Возможные значения: hide, show и nevershow. 
;Значению по умолчанию соответствует hide, nevershow отключает 
;и вышеупомянутую кнопку, а мы используем show — показывать.

ShowInstDetails show


;***********************************************************************************
; На этом в нашем конкретном примере мы закончим определять настройки нашего инсталлятора,
; и перейдем к самой важной логической составляющей любого инсталлятора на языке NSIS – секции.

; Секции — это логический блок команд, наличие которого является обязательным.
; Инсталлятор должен содержать хотя бы одну секцию. Именно в секциях производятся
; действия над файлами, операции с реестром, ярлыками и файловой системой.
; Блок команд, входящих в секцию, обрамляется ключевыми словами Section-SectionEnd.
; Мы поговорим о секциях более подробно позднее, сейчас же нам необходимо создать
; главную секцию для нашего скрипта. Дополняем наш скрипт следующими строчками:


;Memento Settings
!define MEMENTO_REGISTRY_ROOT HKLM
!define MEMENTO_REGISTRY_KEY "Software\Politrend\TradeTerminal"

# restore
Function .onInit

;;  ${If} ${Cmd} `MessageBox MB_YESNO "Would you like to load an example state?" IDYES`
    
;;	DeleteRegKey  HKCU "Software\Politrend\TradeTerminal"


;;	WriteRegDWORD HKCU "Software\Politrend\TradeTerminal" MementoSection_SecDB  0
;;	WriteRegDWORD HKCU "Software\Politrend\TradeTerminal" MementoSection_SecJRE 0
;;	WriteRegDWORD HKCU "Software\Politrend\TradeTerminal" MementoSection_SecClientTerminal  1
;;	WriteRegDWORD HKCU "Software\Politrend\TradeTerminal" MementoSection_SecReports  1


;;  ${EndIf}

  ${MementoSectionRestore}

FunctionEnd



${MementoSection}  "PostgreSQL 8" SecDB
	ExecWait "$EXEDIR\postgresql-8.4.4-1-windows.exe"
${MementoSectionEnd} 

${MementoSection}  "JRE 1.6" SecJRE
	ExecWait "$EXEDIR\jre-6u24-windows-i586-s.exe"
${MementoSectionEnd} 

SectionGroup "!${PRODUCT_NAME}" SecMain
${MementoSection} "Клиентский терминал" SecClientTerminal
	SectionIn RO

	SetOutPath "$INSTDIR"
	File "${pkgdir}\*.*" 
	
	SetShellVarContext All
	CreateDirectory $SMPROGRAMS\${PRODUCT_NAME}-${PRODUCT_VERSION}
	CreateShortCut $SMPROGRAMS\${PRODUCT_NAME}-${PRODUCT_VERSION}\TradeTerminal.lnk $INSTDIR\TradeTerminal.exe
	CreateShortCut $SMPROGRAMS\${PRODUCT_NAME}-${PRODUCT_VERSION}\TradeTerminal.lnk $INSTDIR\TradeTerminal.jar "" $INSTDIR\TT.ico
	CreateShortCut $SMPROGRAMS\${PRODUCT_NAME}-${PRODUCT_VERSION}\InstallDB.lnk $INSTDIR\InstallDB.exe
	CreateShortCut $SMPROGRAMS\${PRODUCT_NAME}-${PRODUCT_VERSION}\InstallDB.lnk $INSTDIR\InstallDB.jar
	CreateShortCut $SMPROGRAMS\${PRODUCT_NAME}-${PRODUCT_VERSION}\InstallDB.lnk $INSTDIR\InstallDB.jar --bm
	
	CreateShortCut $DESKTOP\TradeTerminal-${PRODUCT_VERSION}.lnk $INSTDIR\TradeTerminal.exe 
	CreateShortCut $DESKTOP\TradeTerminal-${PRODUCT_VERSION}.lnk $INSTDIR\TradeTerminal.jar "" $INSTDIR\TT.ico
	
	SetOutPath "$INSTDIR\lib"
	File "${pkgdir}\lib\*.*"
	
	SetOutPath "$INSTDIR\clear_db"
	File "${pkgdir}\clear_db\*.*"
	
	CreateDirectory $INSTDIR\reports
	CreateDirectory $INSTDIR\pbin
		
	SetOutPath "$INSTDIR"
	ExecWait "$INSTDIR\InstallDBc.exe --sm --set-path-pg-tools pbin"	
	
		
	;;MessageBox MB_OK "Установка прошла успешно"
${MementoSectionEnd}

${MementoSection} "Отчеты" SecReports
	SetOutPath "$INSTDIR\reports"
	File "${pkgdir}\reports\*.*"
${MementoSectionEnd}

${MementoSection} "Утилиты Postgre" SecPostgreUtils
	ExecWait "$EXEDIR\vcredist_x86.exe"

	SetOutPath "$INSTDIR\pbin"
	File "${pkgdir}\pbin\*.*"
${MementoSectionEnd}

SectionGroupEnd


# done...

${MementoSectionDone}

# save

Function .onInstSuccess

  ${MementoSectionSave}

FunctionEnd





;--------------------------------
;Descriptions
;Language strings
;LangString DESC_SecDummy "A test section."

;Assign language strings to sections
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
	!insertmacro MUI_DESCRIPTION_TEXT ${SecJRE} "JAVA - платформа, выполняющая программу."
	!insertmacro MUI_DESCRIPTION_TEXT ${SecDB} "Сервер баз данных."
	!insertmacro MUI_DESCRIPTION_TEXT ${SecMain} "Основная рабочая программа."
	!insertmacro MUI_DESCRIPTION_TEXT ${SecClientTerminal} "Клиентская часть программы."
	!insertmacro MUI_DESCRIPTION_TEXT ${SecReports} "Отчеты."
	!insertmacro MUI_DESCRIPTION_TEXT ${SecPostgreUtils} "Клиентские программы Postgre, необходимы для создания резервных копий данных и восстановления их."
!insertmacro MUI_FUNCTION_DESCRIPTION_END




