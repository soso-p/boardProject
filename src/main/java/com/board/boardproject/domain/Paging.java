package com.board.boardproject.domain;

public class Paging {
    private int nowPage; // 현재 페이지
    private int startPage; // 시작 페이지
    private int endPage; // 끝 페이지
    private int total; // 게시글 총 개수
    private int cntPerPage; // 페이지 당 글 개수
    private int lastPage; // 마지막 페이지
    private int start; // sql 쿼리에 쓸 start
    private int end; // sql 쿼리에 쓸 end

    private int cntPage = 5; // 버튼에 보일 페이지 수

    public Paging() {
    }

    public Paging(int total, int nowPage, int cntPerPage) {
        setNowPage(nowPage);
        setCntPerPage(cntPerPage);
        setTotal(total);
        calculateLastPage(getTotal(), getCntPerPage());
        if (nowPage > lastPage) { // 현재 페이지가 마지막 페이지보다 뒤이면 현재 페이지를 마지막 페이지로 변경
            setNowPage(lastPage);
        }
        calculateStartEndPage(getNowPage(), cntPage);
        calculateStartEnd(getNowPage(), getCntPerPage());
    }

    // 제일 마지막 페이지 계산
    public void calculateLastPage(int total, int cntPerPage) {
        setLastPage((int) Math.ceil((double)total / (double)cntPerPage)); // ceil은 올림
    }

    // 시작, 끝 페이지 계산
    public void calculateStartEndPage(int nowPage, int cntPage) {
        // 현재페이지의 2 페이지 앞부터, 2페이지 뒤까지 버튼에 나오도록 설정
        //setEndPage(((int)Math.ceil((double)nowPage / (double)cntPage)) * cntPage);
        setEndPage((int)nowPage+2);

        if (getLastPage() < getEndPage()) {
            setEndPage(getLastPage());
        }

        //setStartPage(getEndPage() - cntPage + 1);
        setStartPage(nowPage-2);
        if (getStartPage() < 1) {
            setStartPage(1);
        }

        // endPage가 startPage보다 크면 endPage를 startPage와 같게 설정
        if (getStartPage() > getEndPage()) {
            setEndPage(getStartPage());
        }
    }

    // DB 쿼리에서 사용할 start, end값 계산
    public void calculateStartEnd(int nowPage, int cntPerPage) {
        setEnd(nowPage * cntPerPage);
        setStart(getEnd() - cntPerPage);
        setEnd(getEnd() - getStart());
    }

    // getter, setter
    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCntPerPage() {
        return cntPerPage;
    }

    public void setCntPerPage(int cntPerPage) {
        this.cntPerPage = cntPerPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
