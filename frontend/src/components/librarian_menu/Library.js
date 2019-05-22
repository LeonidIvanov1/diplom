import React, {Component} from 'react';
import Container from "react-bootstrap/Container";
import {Button, Col, FormControl, InputGroup, Row, Table} from "react-bootstrap";
import ReactPaginate from 'react-paginate';
import {LinkContainer} from "react-router-bootstrap";


class book_collection extends Component {

    perPage = 12;
    url = 'http://localhost:8088/library_war/FrontController?';
    curPage = 0;

    constructor(props) {
        super(props);
        this.state = {
            book_collection: null,
            books: null,
            searchValue: "",
            pageCount: 0,
            show: 'collection'
        }

    }

    getRequestData(data) {
        var formData = new FormData();
        for (var name in data) {
            formData.append(name, data[name]);
        }
        let searchParams = Object.keys(data).map((key) => {
            return encodeURIComponent(key) + '=' + encodeURIComponent(data[key]);
        }).join('&');
        return searchParams;
    }

    componentDidMount() {
        this.getbook_collectionCount();
        this.getbook_collection();

    }

    getbook_collectionCount = () => {
        let data = {
            command: 'GET_book_collection_COUNT',
            search_data: this.state.searchValue
        };
        let req = this.getRequestData(data);
        fetch(this.url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: req
        }).then((response) => response.text())
            .then(response => {
                return JSON.parse(response);
            }).then(data => {
            this.setState({pageCount: Math.ceil(data.value / this.perPage)})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    };

    getBooksCount = () => {
        let data = {
            command: 'GET_book_COUNT',
            search_data: this.state.searchValue
        };
        let req = this.getRequestData(data);
        fetch(this.url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: req
        }).then((response) => response.text())
            .then(response => {
                return JSON.parse(response);
            }).then(data => {
            this.setState({pageCount: Math.ceil(data.value / this.perPage)})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    };

    getbook_collection = () => {
        let data = {
            command: 'GET_book_collections',
            search_data: this.state.searchValue,
            start: 0,
            end: this.perPage
        };
        let req = this.getRequestData(data);
        fetch(this.url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: req
        }).then((response) => response.text())
            .then(response => {
                return JSON.parse(response);
            }).then(data => {
            this.setState({book_collection: data.value})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    };

    getBooks = () => {
        let data = {
            command: 'GET_books',
            search_data: this.state.searchValue,
            start: 0,
            end: this.perPage
        };
        let req = this.getRequestData(data);
        fetch(this.url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: req
        }).then((response) => response.text())
            .then(response => {
                return JSON.parse(response);
            }).then(data => {
            this.setState({books: data.value})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    };


    createbook_collectionRows = () => {
        let book_collection = this.state.book_collection;
        let rows = [];
        if (book_collection != null) {
            for (let i = 0; i < this.state.book_collection.length; i++) {
                let row = <tr key={i + book_collection[i].id} id={book_collection[i].groupID}>
                    <td>{this.curPage * this.perPage + i + 1}</td>
                    <td>{book_collection[i].name}</td>
                    <td>{book_collection[i].author}</td>
                    <td>{book_collection[i].year}</td>
                    <td>{this.setBookType(book_collection[i].literatureType)}</td>
                    <td>{book_collection[i].inStockCount}</td>
                </tr>;
                rows.push(row);
            }
        }
        return rows;
    };

    handlePageClick = data => {
        if (this.state.show === 'collection') {
            let selected = data.selected;
            this.curentPage = selected;
            let offset = Math.ceil(selected * this.perPage);
            let reqData = {
                command: 'GET_book_collection',
                start: offset,
                end: offset + this.perPage,
                search_data: this.state.searchValue
            };
            let req = this.getRequestData(reqData);
            fetch(this.url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                },
                body: req
            }).then((response) => response.text())
                .then(response => {
                    return JSON.parse(response);
                }).then(data => {
                this.setState({book_collection: data.value})
            }).catch(function (error) {
                console.log('There has been a problem with your fetch operation' + error.message);
            });
        } else if (this.state.show === 'books') {
            let selected = data.selected;
            this.curentPage = selected;
            let offset = Math.ceil(selected * this.perPage);
            let reqData = {
                command: 'GET_books',
                start: offset,
                end: offset + this.perPage,
                search_data: this.state.searchValue
            };
            let req = this.getRequestData(reqData);
            fetch(this.url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                },
                body: req
            }).then((response) => response.text())
                .then(response => {
                    return JSON.parse(response);
                }).then(data => {
                this.setState({books: data.value})
            }).catch(function (error) {
                console.log('There has been a problem with your fetch operation' + error.message);
            });
        }


    };

    setBookType(data) {
        switch (data) {
            case 'BOOK':
                return 'Учебное пособие';
            case 'TRAINING':
                return 'Методическое пособие'
        }
    }

    onTableClick = (e) => {
        let id = e.target.parentElement.getAttribute('id');
        console.log(id);
        this.props.history.push("/group_info/" + id);
    };


    showTable = () => {
        if (this.state.show === 'collection') {
            return <Table striped bordered hover className="data-table" onClick={this.onTableClick}>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Название</th>
                    <th>Автор</th>
                    <th>Год</th>
                    <th>Тип</th>
                    <th>Кол-во свободных экземпляров</th>
                </tr>
                </thead>
                <tbody>
                {this.createbook_collectionRows()}
                </tbody>
            </Table>
        } else if (this.state.show === 'books') {
            return <Table striped bordered hover className="data-table" onClick={this.onTableClick}>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Название</th>
                    <th>Автор</th>
                    <th>Год</th>
                    <th>Тип</th>
                    <th>ISBN</th>
                    <th>Статус</th>
                    <th>Держатель</th>

                </tr>
                </thead>
                <tbody>
                {this.createBooks()}
                </tbody>
            </Table>
        }
    };

    getBookStatus(data) {
        switch (data) {
            case 'IN_STOCK':
                return 'В наличии';
            case 'RESERVED':
                return 'Зарезервирована';
            case 'WRITTEN_OFF':
                return  'Списана';
            case 'OUT_OF_STOCK':
                return 'Нет в наличии';
        }
    }
    createBooks = () => {
        let books = this.state.books;
        let rows = [];
        if (books != null) {
            for (let i = 0; i < books.length; i++) {
                let row = <tr key={i + books[i].id} id={books[i].id}>
                    <td>{this.curPage * this.perPage + i + 1}</td>
                    <td>{books[i].name}</td>
                    <td>{books[i].author}</td>
                    <td>{books[i].year}</td>
                    <td>{this.setBookType(books[i].literatureType)}</td>
                    <td>{books[i].isbn}</td>
                    <td>{this.getBookStatus(books[i].literatureStatus)}</td>

                    <td>{this.getHolder(books[i].holder)}</td>

                </tr>;
                rows.push(row);
            }
        }
        return rows;
    };

    getHolder(data) {
        if (data == undefined) {
            return "-"
        } else {
            return data.fullName
        }
    }

    onCollectionSearchClick = () => {
        this.setState({show: 'collection'});
        this.getbook_collectionCount();
        this.getbook_collection();
    };

    onLiteratureSearchClick = () => {
        this.setState({show: 'books'});
        this.getBooksCount();
        this.getBooks();
    };

    render() {

        return (
            <Container fluid="true">
                <Row>
                    <Col>
                        <LinkContainer to="/add_book">
                            <Button variant="primary">Добавить новый экземпляр</Button>
                        </LinkContainer>
                    </Col>
                    <Col>
                        <InputGroup className="mb-3">
                            <FormControl
                                placeholder="Введите данные для поиска"
                                aria-label="Введите данные для поиска"
                                aria-describedby="basic-addon2"
                                value={this.state.searchValue}
                                onChange={(e) => {
                                    this.setState({
                                            searchValue: e.target.value
                                        }
                                    )
                                    ;
                                }}

                            />
                            <InputGroup.Append>
                                <Button variant="primary" onClick={this.onCollectionSearchClick}>Поиск по
                                    коллекциям</Button>
                            </InputGroup.Append>
                            <InputGroup.Append>
                                <Button variant="primary" onClick={this.onLiteratureSearchClick}>Поиск по
                                    экземплярам</Button>
                            </InputGroup.Append>
                        </InputGroup>
                    </Col>
                </Row>
                <Row>
                    {this.showTable()}
                </Row>
                <Row>
                    <Col md="auto" className="paging">
                        <ReactPaginate
                            previousLabel={'Предыдущая'}
                            nextLabel={'Следующая'}
                            breakLabel={'...'}
                            breakClassName={'break-me'}
                            pageCount={this.state.pageCount}
                            marginPagesDisplayed={2}
                            pageRangeDisplayed={5}
                            onPageChange={this.handlePageClick}
                            containerClassName={'pagination'}
                            subContainerClassName={'pages pagination'}
                            activeClassName={'active'}
                        />
                    </Col>
                </Row>
            </Container>

        );
    }
}

export default book_collection;
