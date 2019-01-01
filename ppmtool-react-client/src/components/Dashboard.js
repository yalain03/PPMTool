import React, { Component } from 'react';
import ProjectItem from './Project/ProjectItem';
import CreateProjectButton from './Project/CreateButton';
import { connect } from 'react-redux';
import { getProjects } from '../actions/projectActions';
import PropTypes from 'prop-types';

class Dashboard extends Component {

    componentDidMount() {
        this.props.getProjects();
    }

    render() {        
        const projects = this.props.projects.projects;

        return (
            <div className="projects">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12">
                            <h1 className="display-4 text-center">Projects</h1>
                            <br />
                            <CreateProjectButton />
                            <br />
                            <hr />
                            {
                                projects.map(project => (<ProjectItem key={project.id} project={project} />))
                            }                            
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

Dashboard.protoTypes = ({
    project: PropTypes.object.isRequired,
    getProjects: PropTypes.func.isRequired
});

const mapStateToProps = state => ({
    projects: state.projects
});

export default connect(mapStateToProps, {getProjects})(Dashboard);