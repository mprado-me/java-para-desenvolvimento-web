import java.util.Calendar;

public class Main {
	public static void main(String[] args) {
		ContatoDao dao = new ContatoDao();

		for (int i = 0; i < 10; i++) {
			Contato contato = new Contato();
			contato.setNome("Nome " + i);
			contato.setEmail("contato" + i + "@caelum.com.br");
			contato.setEndereco("Rua " + i);
			contato.setDataNascimento(Calendar.getInstance());
			dao.adiciona(contato);
		}

		for (Contato contato : dao.getLista()) {
			System.out.println("Nome: " + contato.getNome());
			System.out.println("Email: " + contato.getEmail());
			System.out.println("Endereço: " + contato.getEndereco());
			System.out.println("Data de Nascimento: " + contato.getDataNascimento().getTime() + "\n");
		}

		for (Contato contato : dao.getLista()) {
			contato.setNome(contato.getNome() + " [MODIFICADO]");
			dao.altera(contato);
		}

		for (Contato contato : dao.getLista()) {
			System.out.println("Nome: " + contato.getNome());
			System.out.println("Email: " + contato.getEmail());
			System.out.println("Endereço: " + contato.getEndereco());
			System.out.println("Data de Nascimento: " + contato.getDataNascimento().getTime() + "\n");
		}

		for (Contato contato : dao.getLista()) {
			dao.remove(contato);
		}

		System.out.println("dao.getLista().size(): " + dao.getLista().size());
	}
}
