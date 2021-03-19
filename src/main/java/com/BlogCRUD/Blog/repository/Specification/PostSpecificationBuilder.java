package com.BlogCRUD.Blog.repository.Specification;

import com.BlogCRUD.Blog.models.Post;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PostSpecificationBuilder {

    private final List<SearchCriteria> params;
    PostSpecification postSpecification = new PostSpecification();

    public PostSpecificationBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public PostSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Post> build() {
        if (params.size() == 0) {
            return null;
        }
        List<Specification> specs = (List<Specification>) params.stream().map(postSpecification)

    }


}
