/**
 *  파일명 : t_RGrid.js
 *  설  명 : AuiGrid 초기 설정 및 변수 및 메서드 생성 스크립트
 * */

var lpAuiGrid = {};

/**
 * 상수값 설정
 * */
lpAuiGrid.AuiGridStatColId = "rowStats"; // AuiGrid ROW 상태 COL ID
lpAuiGrid.AuiGridStatIns = "I"; // AuiGrid ROW 추가 상태 값
lpAuiGrid.AuiGridStatUpd = "U"; // AuiGrid ROW 수정 상태 값
lpAuiGrid.AuiGridStatDel = "D"; // AuiGrid ROW 삭제 상태 값

lpAuiGrid.AuiGridPropList = "LIST"; // AuiGrid LIST 설정 값
lpAuiGrid.AuiGridPropEdit = "EDIT"; // AuiGrid EDIT 설정 값
lpAuiGrid.AuiGridPropTree = "TREE"; // AuiGrid TREE 설정 값
//추가
//lpAuiGrid.AuiGridProp = ""; // AuiGrid EDIT 설정 값


/**
 * 그리드 기본 설정
 * */
lpAuiGrid.setInitAuiGrid = function (pGObjGrid, addProps) {

    if (AUIGrid.isCreated(pGObjGrid.myGridID)) AUIGrid.destroy(pGObjGrid.myGridID); // 삭제
    pGObjGrid.initGrid(addProps);
    $.extend(pGObjGrid.gridParam.auiGridProps, addProps);

    lpAuiGrid.setAuiGridParam(pGObjGrid); // 그리드 파라메터 기본 설정
    lpAuiGrid.setAuiGridEvent(pGObjGrid); // 그리드 원래 이벤트 재정의
    lpAuiGrid.setAuiGridMethod(pGObjGrid); // 그리드 오브젝트 기본 사용 메서드 정의
};

/**
 * 그리드 파라메터 기본 설정
 * */
lpAuiGrid.setAuiGridParam = function (pGObjGrid) {
    // 그리드
    var auiGridDefaultProps = pGObjGrid.gridParam.auiGridDefaultProps;
    var columnLayout = pGObjGrid.gridParam.columnLayout;    // 컬럼 레이아웃
    var auiGridProps = pGObjGrid.gridParam.auiGridProps;    // 그리드 속성 default속성 위에 덮어씀

    // 컬럼 레이아웃 공통 속성 정의


    // AUIGrid 를 생성 할 때 기본적으로 defaultProps 를 확장하여 생성합니다.
    var defaultProps = {
        selectionMode: "singleRow", // 선택모드[singleCell, singleRow, multipleCells, multipleRows, none]
        noDataMessage: "출력할 데이터가 없습니다.",
        //headerHeight: 24,
        //rowHeight: 24,
        showRowNumColumn: true,
        fillColumnSizeMode: true,
        wrapText: true,
        usePaging: true, // 페이징 사용
        enableFilter: false,
        showPageButtonCount: 10, // 한 화면 페이징 버턴 개수 10개로 지정
        showPageRowSelect: true,
        pageRowCount: 20,   // 한 화면에 출력되는 행 개수 50개로 지정
        //autoGridHeight: true,
    };

    switch (auiGridDefaultProps) {
        // LIST
        case lpAuiGrid.AuiGridPropList :
            defaultProps = $.extend(defaultProps, {
                enableColumnResize: true,
                enableMovingColumn: true,
                enableFilter: false,
            });

            var flatColumnLayout = lpAuiGrid.flatGridData(columnLayout);
            for (var i in flatColumnLayout) {
                if (flatColumnLayout[i].hasOwnProperty("filter")) continue; // 설정한정보가 있으면 실행 안함
                flatColumnLayout[i].filter =  flatColumnLayout[i].filter || {};
                flatColumnLayout[i].filter.showIcon = flatColumnLayout[i].filter.showIcon || true;
            }
            break;

        //EDIT
        case lpAuiGrid.AuiGridPropEdit :
            defaultProps = $.extend(defaultProps, {
                editable: true, //수정가능여부
                showStateColumn: true, // 신규,수정,삭제 아이콘 표시, default:false
                showRowCheckColumn: true, // 행 체크박스 출력여부, dafault:false
                softRemovePolicy: "exceptNew", // 사용자가 추가한 새행은 softRemoveRowMode 적용 안함. 바로 삭제함.
            });
            break;

        //TREE
        case lpAuiGrid.AuiGridPropTree :
            defaultProps = $.extend(defaultProps, {
                enableColumnResize: true,
                enableMovingColumn: true,
                enableFilter: false,
                // 일반 데이터를 트리로 표현할지 여부(treeIdField, treeIdRefField 설정 필수)
                flat2tree: true,
            });
            // filter icon 설정
            var flatColumnLayout = lpAuiGrid.flatGridData(columnLayout);
            for (var i in flatColumnLayout) {
                flatColumnLayout[i].filter =  flatColumnLayout[i].filter || {};
                flatColumnLayout[i].filter.showIcon = flatColumnLayout[i].filter.showIcon || true;
            }
            break;
    }

    AUIGrid.defaultProps = defaultProps;    // 기본속성으로 그리드 생성

    pGObjGrid.auiGridProps = $.extend(true, defaultProps, auiGridProps); //그리드 속성 저장
    AUIGrid.create(pGObjGrid.myGridID, columnLayout, auiGridProps);// 그리드 생성
    lpAuiGrid.hideColumnByHiddenField(pGObjGrid);   //hidden 컬럼 숨기기
};

/**
 * 그리드 원래 이벤트 재정의
 * */
lpAuiGrid.setAuiGridEvent = function (pGObjGrid) {
    // 윈도우 리사이징 이벤트
    window.onresize = function () {
        // 크기가 변경되었을 때 AUIGrid.resize() 함수 호출
        AUIGrid.resize(pGObjGrid.myGridID);
    };
    //
    // AUIGrid.bind(pGObjGrid.myGridID, "pageRowCountChange", function(event ) {
    //     console.log("pageRowCountChange")
    //     AUIGrid.resize(pGObjGrid.myGridID);
    // });
    // AUIGrid.bind(pGObjGrid.myGridID, "ready", function(event ) {
    //     console.log("ready")
    //     AUIGrid.resize(pGObjGrid.myGridID);
    // });

    var bindingEvent = pGObjGrid.setBindingEvent;

    for (var eventType in bindingEvent) {
        AUIGrid.bind(pGObjGrid.myGridID, eventType, bindingEvent[eventType]);
    }
};

/**
 * 그리드 오브젝트 기본 사용 변수, 메서드 정의
 *   -------사용 변수----------------------

 *   -------사용 메서드---------------------
 *  objGrid.setGridData(); // 데이터 로딩후 세팅
 *  pGObjGrid.addRow // 행추가
 *  pGObjGrid.removeCheckedRows // 행삭제
 *  pGObjGrid.getChgDataGrid // 변경 데이터 리턴
 *  pGObjGrid.getSelectRow// 선택 ROW 데이터 리턴
 * */

lpAuiGrid.setAuiGridMethod = function (pGObjGrid) {
    var gridParam = pGObjGrid.gridParam;

    /**
     * 그리드 초기 데이터 설정
     */
    pGObjGrid.setGridData = function (pData) {
        AUIGrid.setGridData(pGObjGrid.myGridID, pData);

        //커스텀 페이징 사용시
        if (pGObjGrid.auiGridProps.useCustomPaging) {
            var selectPage = $("#selectPage").val();
            var totalRowCount = lpCom.isEmpty(pData) ? 0 : pData[0].ALL_CNT;       // 전체 데이터 건수
            var rowCount = $("#pageSize").val();	// 1페이지에서 보여줄 행 수
            var pageButtonCount = 10;		        // 페이지 네비게이션에서 보여줄 페이지의 수
            var currentPage = 1;	                // 현재 페이지
            var totalPage = Math.ceil(totalRowCount / rowCount);	// 전체 페이지 계산

            var retStr = "";
            var prevPage = parseInt((selectPage - 1) / pageButtonCount) * pageButtonCount;
            var nextPage = ((parseInt((selectPage - 1) / pageButtonCount)) * pageButtonCount) + pageButtonCount + 1;

            prevPage = Math.max(0, prevPage);
            nextPage = Math.min(nextPage, totalPage);

            // 처음
            retStr += "<a href='javascript:fnSearch(1)'><span class='aui-grid-paging-number aui-grid-paging-first'>first</span></a>";
            // 이전
            retStr += "<a href='javascript:fnSearch(" + Math.max(1, prevPage) + ")'><span class='aui-grid-paging-number aui-grid-paging-prev'>prev</span></a>";
            for (var i = (prevPage + 1), len = (pageButtonCount + prevPage); i <= len; i++) {
                if (selectPage == i) {
                    retStr += "<span class='aui-grid-paging-number aui-grid-paging-number-selected'>" + i + "</span>";
                } else {
                    retStr += "<a href='javascript:fnSearch(" + i + ")'><span class='aui-grid-paging-number'>";
                    retStr += i;
                    retStr += "</span></a>";
                }
                if (i >= totalPage) {
                    break;
                }
            }
            // 다음
            retStr += "<a href='javascript:fnSearch(" + nextPage + ")'><span class='aui-grid-paging-number aui-grid-paging-next'>next</span></a>";
            // 마지막
            retStr += "<a href='javascript:fnSearch(" + totalPage + ")'><span class='aui-grid-paging-number aui-grid-paging-last'>last</span></a>";

            var pagingDiv = $("<div>").attr("id", "grid_paging")
                .addClass("aui-grid-paging-panel")
                .addClass("my-grid-paging-panel");

            retStr += "<select onchange='javascript:$(\"#pageSize\").val(this.value);fnSearch(1)' class='aui-grid-paging-row-count-select'>";
            retStr += "<option value='10' " + (rowCount == 10 ? 'selected' : '') + ">10</option>";
            retStr += "<option value='20'" + (rowCount == 20 ? 'selected' : '') + ">20</option>";
            retStr += "<option value='30'" + (rowCount == 30 ? 'selected' : '') + ">30</option>";
            retStr += "<option value='40'" + (rowCount == 40 ? 'selected' : '') + ">40</option>";
            retStr += "<option value='50'" + (rowCount == 50 ? 'selected' : '') + ">50</option>";
            retStr += "</select>";

            pagingDiv.append(retStr);

            $(pGObjGrid.myGridID).parent().find("#grid_paging").remove();
            $(pGObjGrid.myGridID).after(pagingDiv);

            //nicescroll 사이즈 재조정
            $(".cScroll").niceScroll().resize();
        }

        if (pGObjGrid.hasOwnProperty("allCntViewObjID")) {
            if(pGObjGrid.auiGridProps.useCustomPaging) {
                $("#" + pGObjGrid.allCntViewObjID).html("총 " + lpCom.formatNumber(lpCom.isEmpty(pData) ? 0 : pData[0].ALL_CNT) + " 건");
            } else {
                $("#" + pGObjGrid.allCntViewObjID).html("총 " + lpCom.formatNumber(pData.length) + " 건");
            }
        }
        //그리드 로딩 제거
        AUIGrid.removeAjaxLoader(pGObjGrid.myGridID);

        //마지막 변경 정보가 있으면 포커스 이동 처리
        if (pGObjGrid.lastRowId != undefined) {
            if (pGObjGrid.auiGridProps.hasOwnProperty("rowIdField")) {
                //트리접기인경우 상위 펼치기
                if (!pGObjGrid.auiGridProps.hasOwnProperty("displayTreeOpen") || pGObjGrid.auiGridProps.displayTreeOpen == false) {
                    var ascendants = AUIGrid.getAscendantsByRowId(pGObjGrid.myGridID, pGObjGrid.lastRowId); // 조상행들
                    if(ascendants && ascendants.length) {
                        for (var i = 0, len = ascendants.length; i < len; i++) {
                            AUIGrid.expandItemByRowId(pGObjGrid.myGridID, ascendants[i][pGObjGrid.auiGridProps.rowIdField], true);
                        }
                    }
                }
                AUIGrid.selectRowsByRowId(pGObjGrid.myGridID, pGObjGrid.lastRowId);
                delete pGObjGrid.lastRowId;
            }
        }

    };

    /**
     * 행추가
     */
    pGObjGrid.addRow = function (pRow, pRowIndex) {
        var row = {};
        if (pRow != undefined) {
            if ($.isArray(pRow)) {
                row = pRow;
            } else {
                row = pRow;
            }
        }

        var rowPos = "first";
        if (pRowIndex != undefined) rowPos = pRowIndex;

        if (pGObjGrid.gridParam.auiGridDefaultProps != lpAuiGrid.AuiGridPropTree) {
            AUIGrid.addRow(pGObjGrid.myGridID, row, rowPos);
        } else {
            //트리일때
            AUIGrid.addTreeRow(pGObjGrid.myGridID, row, row.parentRowId, rowPos);
        }
    };


    /**
     * 선택데이터 삭제
     */
    pGObjGrid.removeCheckedRows = function () {
        AUIGrid.removeCheckedRows(pGObjGrid.myGridID);
    };
	
	/**
     * 선택데이터 삭제
     */
    pGObjGrid.removeSelectedRows = function () {
        AUIGrid.removeRow(pGObjGrid.myGridID, "selectedIndex");
    };

    /**
     * 변경(수정,삭제,추가) 데이터 리턴
     */
    pGObjGrid.getChgDataGrid = function () {
        // 추가된 행 아이템들(배열)
        var addedRowItems = AUIGrid.getAddedRowItems(pGObjGrid.myGridID);
        for (var i in addedRowItems) {
            addedRowItems[i][lpAuiGrid.AuiGridStatColId] = lpAuiGrid.AuiGridStatIns;
        }

        // 수정된 행 아이템들(배열) : 진짜 수정된 필드만 얻음.
        //var editedRowItems = AUIGrid.getEditedRowColumnItems(myGridID);

        // 수정된 행 아이템들(배열) : 수정된 필드와 수정안된 필드 모두를 얻음.
        var editedRowItems = AUIGrid.getEditedRowItems(pGObjGrid.myGridID);
        for (var i in editedRowItems) {
            editedRowItems[i][lpAuiGrid.AuiGridStatColId] = lpAuiGrid.AuiGridStatUpd;
        }

        // 삭제된 행 아이템들(배열)
        var removedRowItems = AUIGrid.getRemovedItems(pGObjGrid.myGridID);
        for (var i in removedRowItems) {
            removedRowItems[i][lpAuiGrid.AuiGridStatColId] = lpAuiGrid.AuiGridStatDel;
        }


        var retDataArr = [].concat(removedRowItems).concat(editedRowItems).concat(addedRowItems);

        if (pGObjGrid.gridParam.auiGridDefaultProps == lpAuiGrid.AuiGridPropTree) {
            //트리일때는 불피요 컬럼 제거 일딴 삭제 필요시.... 다시 사용
            for (var i = 0; i < retDataArr.length; i++) {
                delete retDataArr[i].children;
                delete retDataArr[i]._$depth;
                delete retDataArr[i]._$isBranch;
                delete retDataArr[i]._$isOpen;
                delete retDataArr[i]._$isVisible;
                delete retDataArr[i]._$leafCount;
                delete retDataArr[i]._$parent;
            }
        }

        return retDataArr;
    };

    /**
     * 선택 데이터 리턴
     * 1건일때는 오브제트로, 다건일때는 배열로 리턴
     */
    pGObjGrid.getSelectRowData = function () {
        var selectedItems = AUIGrid.getSelectedItems(pGObjGrid.myGridID);

        var rowDataList = [];
        for (var i = 0; i < selectedItems.length; i++) {
            var rowData = selectedItems[i].item;
            rowData.columnIndex = selectedItems[i].columnIndex;
            rowData.dataField = selectedItems[i].dataField;
            rowData.rowIdValue = selectedItems[i].rowIdValue;
            rowData.rowIndex = selectedItems[i].rowIndex;
            rowDataList.push(rowData);
        }

        if (selectedItems.length == 1) {
            return rowDataList[0]
        } else {
            return rowDataList;
        }
    };
    
    /**
     * 선택(체크) 데이터 리턴
     */
    pGObjGrid.getCheckedRowData = function () {
        var selectedItems = AUIGrid.getCheckedRowItems(pGObjGrid.myGridID);
        
        var rowDataList = [];
        for (var i = 0; i < selectedItems.length; i++) {
            var rowData = selectedItems[i].item;
            rowData.rowIndex = selectedItems[i].rowIndex;
            rowDataList.push(rowData);
        }
        
        return rowDataList;
    };
    /**
     * rowId 값으로 데이터 변경
     */
    pGObjGrid.setRowDataByRowId = function (pRowData) {
        AUIGrid.updateRowsById(pGObjGrid.myGridID, pRowData);
    };

    /**
     * 엑셀 다운로드
     */
    pGObjGrid.exportToXlsx = function (fileName, exportWithStyle) {
        exportWithStyle = exportWithStyle === undefined ? true : exportWithStyle;
        fileName = fileName === undefined ? "export" : fileName;

        AUIGrid.exportToXlsx(pGObjGrid.myGridID, {
            fileName: fileName, //파일명
            exportWithStyle: exportWithStyle, // 스타일 상태 유지 여부(기본값:true)
            exceptColumnFields: AUIGrid.getHiddenColumnDataFields(pGObjGrid.myGridID), // 숨긴 컬럼 포함 안함
        });
    };

    /**
     * 전체보기 삭제후 재생성
     */
    pGObjGrid.showAllGridData = function () {
        AUIGrid.destroy(pGObjGrid.myGridID);
        lpAuiGrid.setInitAuiGrid(pGObjGrid);
    };


    /**
     * 그리드 Validation 체크
     */
    pGObjGrid.validation = function () {
        var columnLayout = lpAuiGrid.flatGridData(pGObjGrid.gridParam.columnLayout);

        // required 컬럼 validation
        var requiredCols = columnLayout.reduce(function (accumulator, currentValue) {
            if (currentValue.hasOwnProperty("required") && currentValue.required === true) {
                accumulator.push(currentValue.dataField);
            }
            return accumulator;
        }, []);

        if (requiredCols.length === 0) {
            return true;
        }

        // 필수값 체크
        // 수정, 추가한 행에 대하여 전체 필드에 대하여 검사
        var isValid = AUIGrid.validateChangedGridData(pGObjGrid.myGridID, requiredCols, "필수 항목입니다.");
        return isValid;
    };

    /**
     * 해당 조건과 일치 하는  ROW 데이터 리턴
     * 예) ObjGrid.getFindRowData({COL_NM:VALUE})
     */
    pGObjGrid.getFindRowData = function (pCond) {
        var rowDatas = AUIGrid.getGridData(pGObjGrid.myGridID);

        var fRowData = {};
        for (var i = 0; i < rowDatas.length; i++) {
            var isFind = true;
            for (var key in pCond) {
                if (rowDatas[i][key] != pCond[key]) {
                    isFind = false;
                    break;
                }
            }
            if (isFind) {
                fRowData = rowDatas[i];
                fRowData.rowIndex = i;
                break;
            }
        }

        return fRowData;
    };

    /**
     * 선택 로우 데이터 가져오기
     */
    pGObjGrid.getSelectedRowItems = function (msg) {
        return AUIGrid.getSelectedItems(pGObjGrid.myGridID)[0];
    };

    /**
     * empty 그리드 데이터 생성
     */
    pGObjGrid.getEmptyItem = function (exclude) {
        if(lpCom.isEmpty(exclude)) exclude = [];

        var data = lpAuiGrid.flatGridData(StdGrid.gridParam.columnLayout);
        console.log(data);
        var emptyItem = {};
        for (var i in data) {
            var field = data[i].dataField;

            if(exclude.indexOf(field) > -1) continue;

            if((data[i].hasOwnProperty("dataType")) && data[i].dataType === "numeric") {
                emptyItem[field] = 0;
            } else {
                emptyItem[field] = '';
            }
        }

        return emptyItem;
    };

    /**
     * ToastMessage 표시
     */
    pGObjGrid.showToastMessage = function (rowIndex , colIndex , msg) {
        rowIndex = rowIndex || 0;
        colIndex = colIndex || 0;
        AUIGrid.showToastMessage(pGObjGrid.myGridID, rowIndex, colIndex, msg);
    };

    /*
     * * ToastMessage 표시 데이터 필드값으로
     */
    pGObjGrid.showToastMessageByDataField = function (rowIndex , dataField , msg) {
        var colIndex = AUIGrid.getColumnIndexByDataField(pGObjGrid.myGridID, dataField);

        rowIndex = rowIndex || 0;
        colIndex = colIndex || 0;
        AUIGrid.showToastMessage(pGObjGrid.myGridID, rowIndex, colIndex, msg);
    };
	
	/**
     * 로우 선택하기
     */
    pGObjGrid.setSelectionByIndex = function (rowIdx, celIdx) {
        return AUIGrid.setSelectionByIndex(pGObjGrid.myGridID, rowIdx, celIdx);
    };

};
/**
 * 그리드 공통 사용 메서드 정의
 *
 * */

/**
 * hidden 필드 가 true일 경우 해당 컬럼 숨김 처리
 */
lpAuiGrid.hideColumnByHiddenField = function (pGObjGrid) {
    var columnLayout = lpAuiGrid.flatGridData(pGObjGrid.gridParam.columnLayout);


    // hidden 컬럼 숨기기
    var hideCols = columnLayout.reduce(function (accumulator, currentValue) {
        if (currentValue.hasOwnProperty("hidden") && currentValue.hidden === true) {
            accumulator.push(currentValue.dataField);
        }
        return accumulator;
    }, []);

    AUIGrid.hideColumnByDataField(pGObjGrid.myGridID, hideCols);
};

/**
 * DropDownListRenderer 리턴
 */
lpAuiGrid.getDropDownListRenderer = function (codeList) {
    var renderer = {
        type: "DropDownListRenderer",
        list: codeList,
        keyField: "CD", // key 에 해당되는 필드명
        valueField: "CD_NM" // value 에 해당되는 필드명
    };

    return renderer;
};

/**
 * columnLayout dataFieldf를 가진 1차원 배열로 반환
 */
lpAuiGrid.flatGridData = function(list) {
    var toReturn = [];

    for (var i in list) {
        if(list[i].hasOwnProperty("children")) {
            var flatList =  lpAuiGrid.flatGridData(list[i].children);
            toReturn = toReturn.concat(flatList);
        } else if(list[i].hasOwnProperty("dataField")) {
            toReturn.push(list[i]);
        }
    }

    return toReturn;
};

//TODO
lpAuiGrid.todo = function () {
    console.log("개발중 추가... 공통적인것들")
};
