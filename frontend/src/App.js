import React, {Component} from 'react';
import './App.css';
import Header from "./components/Header";
import Footer from "./components/Footer";
import Container from "react-bootstrap/Container";
import {Row} from "react-bootstrap";
import Col from "react-bootstrap/Col";
import HomePageContent from "./components/HomePageContent";
import {Route} from "react-router-dom";
import FAQContent from "./components/FAQContent";
import UserMenu from "./components/user_menu/UserMenu";
import ContactsContent from "./components/ContactsContent";
import Users from "./components/admin_menu/Users";
import User from "./components/admin_menu/User";
import Groups from "./components/admin_menu/Groups";
import Group from "./components/admin_menu/Group";
import Disciplines from "./components/admin_menu/Disciplines";
import Discipline from "./components/admin_menu/Discipline";
import Specialties from "./components/admin_menu/Specialties";
import Specialty from "./components/admin_menu/Specialty";
import Library from "./components/librarian_menu/Library";
import Book from "./components/librarian_menu/Book";
import Reader from "./components/librarian_menu/Reader";
import Readers from "./components/librarian_menu/Readers";
import Fgos from "./components/librarian_menu/Fgos";
import FGOSCheck from "./components/librarian_menu/FGOSCheck";
import Reports from "./components/librarian_menu/Reports";
import Fund from "./components/librarian_menu/Fund";
import Debtors from "./components/librarian_menu/Debtors";
import TakenLiterature from "./components/student_menu/TakenLiterature";
import UserLibrary from "./components/student_menu/UserLibrary";
import ReserveRequest from "./components/teacher_menu/ReserveRequest";
import ReserveRequestList from "./components/teacher_menu/ReserveRequestList";

class App extends Component {


    render() {
        return (
            <Container fluid={true}>
                <Row>
                    <Header/>
                </Row>

                <Row>
                    <Col md="3">
                        <UserMenu/>
                    </Col>
                    <Col md="9">
                        <div className="content-data">
                            <Route exact path="/" component={HomePageContent}/>
                            <Route path="/faq" component={FAQContent}/>
                            <Route path="/contacts" component={ContactsContent}/>
                            <Route path="/users" component={Users}/>
                            <Route path="/students_groups" component={Groups}/>
                            <Route path="/disciplines" component={Disciplines}/>
                            <Route path="/specialties" component={Specialties}/>
                            <Route path="/library" component={Library}/>
                            <Route path="/readers" component={Readers}/>
                            <Route path="/fgos" component={Fgos}/>
                            <Route path="/taken_literature" component={TakenLiterature}/>
                            <Route path="/reports" component={Reports}/>
                            <Route path="/reserve" component={ReserveRequest}/>
                            <Route path="/reserve_list" component={ReserveRequestList}/>
                            <Route path="/reports/fgos" component={FGOSCheck}/>
                            <Route path="/reports/fund" component={Fund}/>
                            <Route path="/user_library" component={UserLibrary}/>
                            <Route path="/reports/debtors" component={Debtors}/>
                            <Route path="/add_user" component={User} command={'add_user'}/>
                            <Route path="/add_group" component={Group} command={'add_group'}/>
                            <Route path="/add_discipline" component={Discipline} command={'add_discipline'}/>
                            <Route path="/add_specialty" component={Specialty} command={'add_specialty'}/>
                            <Route path="/add_book" component={Book} command={'add_book'}/>
                            <Route path="/user_info/:id" component={User} command={'user_info'}/>
                            <Route path="/group_info/:id" component={Group} command={'user_info'}/>
                            <Route path="/discipline_info/:id" component={Discipline} command={'discipline_info'}/>
                            <Route path="/specialty_info/:id" component={Specialty} command={'specialty_info'}/>
                            <Route path="/book_info/:id" component={Book} command={'book_info'}/>
                            <Route path="/reader_info/:id" component={Reader} command={'reader_info'}/>
                        </div>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Footer/>
                    </Col>
                </Row>
            </Container>
        );
    }
}

export default App;
