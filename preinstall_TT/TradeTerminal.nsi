;��� ����������
!define PRODUCT_NAME "TradeTerminal"
;������ ����������
!define PRODUCT_VERSION "1.3.1"
;�����, ��� ����� ��������� �������� �����, ���������� ������.
!define pkgdir "TT"


!include "MUI2.nsh"
!include "Sections.nsh"
!include "LogicLib.nsh"
!include "Memento.nsh"
!include "WordFunc.nsh"

; �� ���������
SetCompressor /SOLID lzma

; ��������� MUI_ABORTWARNING ����������, �������� �� �������������� 
; ��� �������� ����������� �������������. �� ���� ������: ��� ������������� 
; ������ �������� ��������� ..��� ��� � � ������ ��� � ����.
!define MUI_ABORTWARNING

;; ��������� MUI_ICON ���������� ������ �����������: 
;; !define MUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\modern-install.ico"
;; ����� ${NSISDIR} - ��������� ���������, �������� ���� � �����, ���� ���������� ��� NSIS.
;; ���������������� �������� ���������:



;����� ���������� ������������ �� ����������, ������� ����� ����� ��������� ������������.
;��� ������������ ����� ����� �������� - ��������� ����������� ������ ����, ������������,
; ��� �������� ����������, � ����� MUI.nsh.
;� ������ ������� ��� �����������
;    * �������� �����������
;    * �������� ��������� ���������� ������������
;    * �������� ���������� ������
;��������������, ���������� �������� ��������� ������ � ��� ������:


!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_LICENSE "${pkgdir}\License.txt"
!insertmacro MUI_PAGE_COMPONENTS
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH

!insertmacro MUI_UNPAGE_WELCOME
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES




;���� �� �����, ����� ��� �������� ������ ������� ���� �� ������� �����, ���������� ����� �������� ����� ������� �����������:
!insertmacro MUI_LANGUAGE "Russian"


;���������� ����������� ������ ����� ���������, ���������� ��� ����������, 
;����� ���, ��������, ���������� Microsoft Office�. ����� ����� ��������, ��������� ��� ��������� ����������:
;    * ������� Name ������ �������� ������ ������������ �� ������ �����. � ����� ������ �� ����� ������������ � � ��������� ����:

Name "${PRODUCT_NAME}"

;    * ������� Caption ������ ��������� ���� �����������. Ÿ �� ������� ������

Caption "��������� ${PRODUCT_NAME} ${PRODUCT_VERSION}"

;��� ����� ������������ �������� �������� OutFile. ��� ���:
OutFile "${PRODUCT_NAME}-${PRODUCT_VERSION}.exe"
InstallDir "$PROGRAMFILES\TT"


;��������� �������, ShowInstDetails, ������� �����������, 
;��� � ���� ���� ���������� ������������ ������ ���� ����� 
;�������� ��� ��������. �� ��������� �� �����, � ���������� 
;������ �� ������� ������ �������. ��������� ��������: hide, show � nevershow. 
;�������� �� ��������� ������������� hide, nevershow ��������� 
;� �������������� ������, � �� ���������� show � ����������.

ShowInstDetails show


;***********************************************************************************
; �� ���� � ����� ���������� ������� �� �������� ���������� ��������� ������ ������������,
; � �������� � ����� ������ ���������� ������������ ������ ������������ �� ����� NSIS � ������.

; ������ � ��� ���������� ���� ������, ������� �������� �������� ������������.
; ����������� ������ ��������� ���� �� ���� ������. ������ � ������� ������������
; �������� ��� �������, �������� � ��������, �������� � �������� ��������.
; ���� ������, �������� � ������, ����������� ��������� ������� Section-SectionEnd.
; �� ��������� � ������� ����� �������� �������, ������ �� ��� ���������� �������
; ������� ������ ��� ������ �������. ��������� ��� ������ ���������� ���������:


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
${MementoSection} "���������� ��������" SecClientTerminal
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
	
		
	;;MessageBox MB_OK "��������� ������ �������"
${MementoSectionEnd}

${MementoSection} "������" SecReports
	SetOutPath "$INSTDIR\reports"
	File "${pkgdir}\reports\*.*"
${MementoSectionEnd}

${MementoSection} "������� Postgre" SecPostgreUtils
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
	!insertmacro MUI_DESCRIPTION_TEXT ${SecJRE} "JAVA - ���������, ����������� ���������."
	!insertmacro MUI_DESCRIPTION_TEXT ${SecDB} "������ ��� ������."
	!insertmacro MUI_DESCRIPTION_TEXT ${SecMain} "�������� ������� ���������."
	!insertmacro MUI_DESCRIPTION_TEXT ${SecClientTerminal} "���������� ����� ���������."
	!insertmacro MUI_DESCRIPTION_TEXT ${SecReports} "������."
	!insertmacro MUI_DESCRIPTION_TEXT ${SecPostgreUtils} "���������� ��������� Postgre, ���������� ��� �������� ��������� ����� ������ � �������������� ��."
!insertmacro MUI_FUNCTION_DESCRIPTION_END




