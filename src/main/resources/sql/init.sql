-- 创建数据库
CREATE DATABASE student_db;

-- 连接到新创建的数据库
\c student_db;

-- 创建学生表
CREATE TABLE IF NOT EXISTS students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INTEGER,
    address TEXT,
    enrollment_date DATE,
    additional_info JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 创建索引
CREATE INDEX idx_student_name ON students(name);
CREATE INDEX idx_student_age ON students(age);
CREATE INDEX idx_student_enrollment_date ON students(enrollment_date);

-- 创建全文搜索索引
CREATE INDEX idx_student_full_text ON students USING GIN (to_tsvector('simple', name || ' ' || COALESCE(address, '')));

-- 创建JSON索引
CREATE INDEX idx_student_additional_info ON students USING GIN (additional_info);

-- 创建更新时间触发器
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_student_updated_at
    BEFORE UPDATE ON students
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- 添加一些测试数据
INSERT INTO students (name, age, address, enrollment_date, additional_info)
VALUES 
    ('张三', 20, '北京市海淀区', '2023-09-01', '{"hobby": "编程", "email": "zhangsan@example.com"}'::jsonb),
    ('李四', 21, '上海市浦东新区', '2023-09-01', '{"hobby": "读书", "email": "lisi@example.com"}'::jsonb),
    ('王五', 19, '广州市天河区', '2023-09-02', '{"hobby": "运动", "email": "wangwu@example.com"}'::jsonb);
