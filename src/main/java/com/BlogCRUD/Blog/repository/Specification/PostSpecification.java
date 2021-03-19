package com.BlogCRUD.Blog.repository.Specification;

import com.BlogCRUD.Blog.models.Post;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.function.Function;

public class PostSpecification implements Specification<Post>, Function<SearchCriteria, R> {
    private SearchCriteria criteria;

    @Override
    public Specification<Post> and(Specification<Post> other) {
        return null;
    }

    @Override
    public Specification<Post> or(Specification<Post> other) {
        return null;
    }

    @Override
    public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThanOrEqualTo(root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if( root.get(criteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(root.<String> get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }

    @Override
    public R apply(SearchCriteria searchCriteria) {
        return null;
    }

    @Override
    public <V> Function<V, R> compose(Function<? super V, ? extends SearchCriteria> before) {
        return null;
    }

    @Override
    public <V> Function<SearchCriteria, V> andThen(Function<? super R, ? extends V> after) {
        return null;
    }
}
