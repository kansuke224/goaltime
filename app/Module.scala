import com.google.inject.AbstractModule
import domain.repository.ProjectPropertyRepository
import infrastructure.repository.ProjectPropertyRepositoryImpl
import play.api.{Configuration, Environment}

class Module(environment: Environment, configuration: Configuration) extends AbstractModule{

  def configure() = {
    bind(classOf[ProjectPropertyRepository]).to(classOf[ProjectPropertyRepositoryImpl])
  }

}
