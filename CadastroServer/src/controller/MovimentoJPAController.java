/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import src.model.Movimento;
import src.model.Pessoa;
import src.model.Produto;
import src.model.Usuario;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author _joao
 */
public class MovimentoJPAController implements Serializable {

    private EntityManagerFactory emf = null;

    public MovimentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Movimento movimento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Pessoa pessoa = movimento.getPessoa();
            if (pessoa != null) {
                pessoa = em.getReference(pessoa.getClass(), pessoa.getIdPessoa());
                movimento.setPessoa(pessoa);
            }
            Produto produto = movimento.getProduto();
            if (produto != null) {
                produto = em.getReference(produto.getClass(), produto.getIdProduto());
                movimento.setProduto(produto);
            }
            Usuario usuario = movimento.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdUsuario());
                movimento.setUsuario(usuario);
            }

            em.persist(movimento);

            if (pessoa != null) {
                pessoa.getMovimentoList().add(movimento);
                em.merge(pessoa);
            }
            if (produto != null) {
                produto.getMovimentoList().add(movimento);
                em.merge(produto);
            }
            if (usuario != null) {
                usuario.getMovimentoList().add(movimento);
                em.merge(usuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Movimento movimento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Movimento persistentMovimento = em.find(Movimento.class, movimento.getIdMovimento());
            Pessoa pessoaOld = persistentMovimento.getPessoa();
            Pessoa pessoaNew = movimento.getPessoa();
            Produto produtoOld = persistentMovimento.getProduto();
            Produto produtoNew = movimento.getProduto();
            Usuario usuarioOld = persistentMovimento.getUsuario();
            Usuario usuarioNew = movimento.getUsuario();

            if (pessoaNew != null) {
                pessoaNew = em.getReference(pessoaNew.getClass(), pessoaNew.getIdPessoa());
                movimento.setPessoa(pessoaNew);
            }
            if (produtoNew != null) {
                produtoNew = em.getReference(produtoNew.getClass(), produtoNew.getIdProduto());
                movimento.setProduto(produtoNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdUsuario());
                movimento.setUsuario(usuarioNew);
            }

            movimento = em.merge(movimento);

            if (pessoaOld != null && !pessoaOld.equals(pessoaNew)) {
                pessoaOld.getMovimentoList().remove(movimento);
                em.merge(pessoaOld);
            }
            if (pessoaNew != null && !pessoaNew.equals(pessoaOld)) {
                pessoaNew.getMovimentoList().add(movimento);
                em.merge(pessoaNew);
            }
            if (produtoOld != null && !produtoOld.equals(produtoNew)) {
                produtoOld.getMovimentoList().remove(movimento);
                em.merge(produtoOld);
            }
            if (produtoNew != null && !produtoNew.equals(produtoOld)) {
                produtoNew.getMovimentoList().add(movimento);
                em.merge(produtoNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getMovimentoList().remove(movimento);
                em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getMovimentoList().add(movimento);
                em.merge(usuarioNew);
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = movimento.getIdMovimento();
                if (findMovimento(id) == null) {
                    throw new NonexistentEntityException("The movimento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Movimento movimento;
            try {
                movimento = em.getReference(Movimento.class, id);
                movimento.getIdMovimento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movimento with id " + id + " no longer exists.", enfe);
            }
            Pessoa pessoa = movimento.getPessoa();
            if (pessoa != null) {
                pessoa.getMovimentoList().remove(movimento);
                em.merge(pessoa);
            }
            Produto produto = movimento.getProduto();
            if (produto != null) {
                produto.getMovimentoList().remove(movimento);
                em.merge(produto);
            }
            Usuario usuario = movimento.getUsuario();
            if (usuario != null) {
                usuario.getMovimentoList().remove(movimento);
                em.merge(usuario);
            }
            em.remove(movimento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Movimento> findMovimentoEntities() {
        return findMovimentoEntities(true, -1, -1);
    }

    public List<Movimento> findMovimentoEntities(int maxResults, int firstResult) {
        return findMovimentoEntities(false, maxResults, firstResult);
    }

    private List<Movimento> findMovimentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Movimento> cq = em.getCriteriaBuilder().createQuery(Movimento.class);
            cq.select(cq.from(Movimento.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Movimento findMovimento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Movimento.class, id);
        } finally {
            em.close();
        }
    }

    public int getMovimentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            Root<Movimento> rt = cq.from(Movimento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}